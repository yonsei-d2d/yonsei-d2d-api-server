package com.gdgotp.d2d.assistant.service;

import com.gdgotp.d2d.assistant.dto.*;
import com.gdgotp.d2d.assistant.model.RouteRunState;
import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.location.mapper.LocationMapper;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.route.mapper.RouteMapper;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;

@Service
public class OpenAIServiceImpl implements OpenAIService {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;



    private final AssistantService service;
    private final RouteRunState runState;

    private final List<FunctionCallback> callbacks;

    public OpenAIServiceImpl(AssistantService service, RouteRunState runState, View error) {
        this.service = service;
        this.runState = runState;

        this.callbacks = List.of(
            FunctionCallback.builder()
                    .function("setMode", service::setMode)
                    .inputType(SetModeDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("findLocationByName", service::findLocationByName)
                    .inputType(FindLocationByNameDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("findLocationByTag", service::findLocationByTag)
                    .inputType(FindLocationByTagDto.class)
                    .build(),
            FunctionCallback.builder()
                    .function("generateRoute", service::generateRoute)
                    .inputType(GenerateRouteDto.class)
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
    public AssistantResponseDto queryAssistant(String query) {
        SystemMessage systemMessage = new SystemMessage(
                "You are a AI Map Assistant. You need to control Map engine with given functions." +
                "Function List: [setMode, findLocationByName, findLocationByTag, generateRoute, setMarker, reportError]" +
                "First, you need to figure out what feature is user requesting now." +
                "If user request to generate a route, you must call setMode to set the Map engine ROUTE mode." +
                "Otherwise if user request to find a single location, you must call setMode to set the Map engine MARKER mode." +
                "Both Route Mode and Marker Mode, You need Location ID which is UUID type, of user requested locations to run Map Engine." +
                "Here is the instruction of Route Mode." +
                "You must find location id of origin and destination first." +
                "After finding origin and destination then now you can find waypoint ids." +
                "Waypoint MUST be located between the origin and the destination or not far from them. CALCULATE THE LATLNG WITH UTMOST CAUTION and find the most appropriate waypoint." +
                "If all waypoint location id are found, call generateRoute function." +
                "if user did not requested waypoint, then you can call generateRoute with empty list []." +
                "Here is the instruction fo Marker Mode." +
                "You must find location id of the most appropriate location." +
                "If you find location id then call setMarker to set target location." +
                "Now here is the instruction for you to query Location Id" +
                "If user requested specific building name, you can find it with findLocationByName. you should choose appropriate in the result list." +
                "Otherwise, if user requested characteristics of location or type of location, you can find it with findLocationByTag function. you should choose appropriate location in the result list." +
                "If function returned null, try another function just few more time." +
                "If multiple attempts returned null then DO NOT PROCEED and report error. you can report error with reportError function." +
                "while reporting error, you must provide error message." +
                "You don't need to explain the route detail. just explain the location name you found." +
                "example: 과학관에서 스위츠카페를 경유해서 대우관으로 가는 경로를 찾았어요"
        );
        UserMessage userMessage = new UserMessage(query);
        var openAiApi = new OpenAiApi(this.apiKey);
        var openAiChatOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(callbacks)
                .withModel(OpenAiApi.ChatModel.GPT_4_O)
                .withFunction("setMode")
                .withFunction("findLocationByName")
                .withFunction("findLocationByTag")
                .withFunction("setMarker")
                .withFunction("generateRoute")
                .withFunction("reportError")
                .build();
        System.out.println(openAiChatOptions);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage), openAiChatOptions);
        System.out.println(prompt);
        ChatResponse response = new OpenAiChatModel(openAiApi, openAiChatOptions).call(prompt);

        if (runState.isMarker()) {
            Location location = runState.getTargetLocation();
            if (location.getType() == null) location.setType(LocationType.PLACE);
            return AssistantResponseDto.builder()
                    .answer(response.getResult().getOutput().getContent())
                    .location(LocationMapper.toLocationResponseDto(location))
                    .build();
        }
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
}
