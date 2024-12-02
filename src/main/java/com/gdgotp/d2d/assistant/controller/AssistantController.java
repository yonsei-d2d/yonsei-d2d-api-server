package com.gdgotp.d2d.assistant.controller;

import com.gdgotp.d2d.assistant.dto.AssistantRequestDto;
import com.gdgotp.d2d.assistant.dto.AssistantResponseDto;
import com.gdgotp.d2d.assistant.service.OpenAIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assistant")
public class AssistantController {

    private final OpenAIService service;

    public AssistantController(OpenAIService service) {
        this.service = service;
    }

    @PostMapping("")
    @ResponseBody
    public AssistantResponseDto query(@RequestBody AssistantRequestDto input) {
        return service.query(input.getQuery());
    }
}
