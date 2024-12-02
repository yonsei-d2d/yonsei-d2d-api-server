package com.gdgotp.d2d.assistant.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.Data;

@Data
@JsonClassDescription("You can find location ID by tag. Tag must be on of the following lise [photo, restaurant, cafeteria, store, cafe, convenience]")
public class FindLocationByTagDto {
    private String tag;
}
