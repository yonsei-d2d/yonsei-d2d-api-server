package com.gdgotp.d2d.assistant.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssistantRequestDto {
    @NotEmpty
    private String query;
}
