package com.gdgotp.d2d.chatbot;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * Handles user input to generate a route.
     * @param userInput The natural language input from the user.
     * @return A map containing the chatbot response and route data.
     */
    @PostMapping("/route")
    public Map<String, Object> processRoutingInput(@RequestParam String userInput) {
        return chatbotService.processRoutingInput(userInput);
    }
}
