package com.gdgotp.d2d.infra.openai;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class OpenAiClient {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private OpenAiChatModel model;

    public OpenAiClient() {
        var openAiApi = new OpenAiApi(this.apiKey);
        var openAiChatOptions = OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4_O_MINI)
                .build();
        model = new OpenAiChatModel(openAiApi, openAiChatOptions);
    }

}
