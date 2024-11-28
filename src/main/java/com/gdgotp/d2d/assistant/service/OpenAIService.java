package com.gdgotp.d2d.assistant.service;

import com.gdgotp.d2d.assistant.dto.AssistantResponseDto;

public interface OpenAIService {
    public AssistantResponseDto queryAssistant(String query);
}
