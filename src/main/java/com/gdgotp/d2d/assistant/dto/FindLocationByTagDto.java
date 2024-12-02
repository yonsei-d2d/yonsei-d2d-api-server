package com.gdgotp.d2d.assistant.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.Data;

@Data
@JsonClassDescription("You can find location ID by tag. Tag must be on of the following lise [photo, restaurant, cafeteria, souvenir, stationery, cafe, convenience, bank, museum, flower, postoffice, stroll]")
public class FindLocationByTagDto {
    private String tag;
}
