package com.gdgotp.d2d.chatbot;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GroqClient {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;

    private final OpenAiChatModel model;

    public GroqClient() {
        var groqApi = new OpenAiApi(apiKey, baseUrl);
        var groqChatOptions = OpenAiChatOptions.builder()
                .withModel("llama3-70b-8192") 
                .build();
        model = new OpenAiChatModel(groqApi, groqChatOptions);
    }

    /**
     * Sends a prompt to the Groq chatbot API and returns the generated response.
     * @param prompt The prompt to send.
     * @return The generated response.
     */
    public String getChatResponse(String prompt) {
        return model.chat().prompt().user(prompt).call().content();
    }
}
