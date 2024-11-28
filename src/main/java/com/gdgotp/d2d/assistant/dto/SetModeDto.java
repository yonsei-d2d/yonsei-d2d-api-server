package com.gdgotp.d2d.assistant.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
@JsonClassDescription("You need to set up the mode first")
public class SetModeDto {
    @JsonPropertyDescription("mode must be ROUTE or MARKER")
    private String mode;
}