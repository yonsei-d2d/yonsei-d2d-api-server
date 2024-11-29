package com.gdgotp.d2d.assistant.service;

import com.gdgotp.d2d.assistant.dto.*;
import com.gdgotp.d2d.assistant.model.RouteRunState;
import com.gdgotp.d2d.assistant.prompt.OpenAIPrompt;
import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.location.mapper.LocationMapper;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.route.mapper.RouteMapper;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;



    private final AssistantService service;
    private final RouteRunState runState;

    private final List<FunctionCallback> routeCallbacks;
    private final List<FunctionCallback> markerCallbacks;

    public OpenAIServiceImpl(AssistantService service, RouteRunState runState, View error) {
        this.service = service;
        this.runState = runState;

        this.routeCallbacks = List.of(
            FunctionCallback.builder()
                    .function("findLocationByName", service::findLocationByName)
                    .inputType(FindLocationByNameDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("findLocationByTag", service::findLocationByTag)
                    .inputType(FindLocationByTagDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("findLocationByRoom", service::findLocationByRoom)
                    .inputType(FindLocationByRoomDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("generateRoute", service::generateRoute)
                    .inputType(GenerateRouteDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("reportError", service::reportError)
                    .inputType(ReportErrorDto.class)
                    .build()
        );

        this.markerCallbacks = List.of(
            FunctionCallback.builder()
                    .function("findLocationByName", service::findLocationByName)
                    .inputType(FindLocationByNameDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("findLocationByTag", service::findLocationByTag)
                    .inputType(FindLocationByTagDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("findLocationByRoom", service::findLocationByRoom)
                    .inputType(FindLocationByRoomDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("setMarker", service::setMarker)
                    .inputType(SetMarkerDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("reportError", service::reportError)
                    .inputType(ReportErrorDto.class)
                    .build()
        );
    }

    @Override
    public AssistantResponseDto query(String query) {
        BeanOutputConverter<ModeDto> converter = new BeanOutputConverter<>(ModeDto.class);
        PromptTemplate promptTemplate = new PromptTemplate(OpenAIPrompt.ModePrompt, Map.of("userInput", query, "format", converter.getFormat()));

        var openAiChatOptions = OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4_O_MINI)
                .build();
        Prompt prompt = new Prompt(promptTemplate.createMessage(), openAiChatOptions);
        ChatResponse response = new OpenAiChatModel(new OpenAiApi(this.apiKey), openAiChatOptions).call(prompt);

        ModeDto output = converter.convert(response.getResult().getOutput().getContent());

        if (output == null) return AssistantResponseDto.builder()
                .answer("요청을 처리하는 중 문제가 발생하였습니다.")
                .build();

        System.out.println(output);
        return switch (output.getMode()) {
            case MARKER -> processMarker(query);
            case ROUTE -> processRoute(query);
            default -> AssistantResponseDto.builder()
                    .answer(output.getOutput())
                    .build();
        };
    }

    private AssistantResponseDto processRoute(String query) {
        SystemMessage systemMessage = new SystemMessage(OpenAIPrompt.RoutePrompt);
        UserMessage userMessage = new UserMessage(query);

        var openAiChatOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(routeCallbacks)
                .withModel(OpenAiApi.ChatModel.GPT_4_O)
                .withFunction("findLocationByName")
                .withFunction("findLocationByTag")
                .withFunction("findLocationByRoom")
                .withFunction("generateRoute")
                .withFunction("reportError")
                .build();

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage), openAiChatOptions);
        ChatResponse response = new OpenAiChatModel(new OpenAiApi(this.apiKey), openAiChatOptions).call(prompt);


        if (runState.getRouteResult() == null) {
            var errorMessage = runState.getError() == null ? "요청을 이해하지 못했어요. 다시 시도해주세요." : runState.getError();
            return AssistantResponseDto.builder()
                    .answer(errorMessage)
                    .build();
        }
        return AssistantResponseDto.builder()
                .answer(response.getResult().getOutput().getContent())
                .route(RouteMapper.toRouteResponseDto(runState.getRouteResult()))
                .build();
    }

    private AssistantResponseDto processMarker(String query) {
        SystemMessage systemMessage = new SystemMessage(OpenAIPrompt.MarkerPrompt);
        UserMessage userMessage = new UserMessage(query);

        var openAiChatOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(markerCallbacks)
                .withModel(OpenAiApi.ChatModel.GPT_4_O)
                .withFunction("findLocationByName")
                .withFunction("findLocationByTag")
                .withFunction("findLocationByRoom")
                .withFunction("setMarker")
                .withFunction("reportError")
                .build();

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage), openAiChatOptions);
        ChatResponse response = new OpenAiChatModel(new OpenAiApi(this.apiKey), openAiChatOptions).call(prompt);


        if (runState.getTargetLocation() == null) {
            var errorMessage = runState.getError() == null ? "요청을 이해하지 못했어요. 다시 시도해주세요." : runState.getError();
            return AssistantResponseDto.builder()
                    .answer(errorMessage)
                    .build();
        }
        return AssistantResponseDto.builder()
                .answer(response.getResult().getOutput().getContent())
                .location(LocationMapper.toLocationResponseDto(runState.getTargetLocation()))
                .build();
    }
}
