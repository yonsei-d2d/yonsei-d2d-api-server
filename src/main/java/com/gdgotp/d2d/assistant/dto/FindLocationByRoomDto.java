package com.gdgotp.d2d.assistant.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.Data;

@Data
@JsonClassDescription("You can find location ID by room number. room number is consist of korean and digits, also can contain alphaber")
public class FindLocationByRoomDto {
    private String room;
}
