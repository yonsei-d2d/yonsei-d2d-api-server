package com.gdgotp.d2d.assistant.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.Builder;
import lombok.Data;

@Data
@JsonClassDescription("You can report error.")
public class ReportErrorDto {
    private String errorMessage;
}