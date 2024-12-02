package com.gdgotp.d2d.assistant.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.Data;

@Data
@JsonClassDescription("You can find location ID by name")
public class FindLocationByNameDto {
    private String name;
}
