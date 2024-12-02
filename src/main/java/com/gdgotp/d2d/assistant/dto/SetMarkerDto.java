package com.gdgotp.d2d.assistant.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
@JsonClassDescription("You can set marker by location id.")
public class SetMarkerDto {
    @JsonPropertyDescription("location id of requested location")
    private String locationId;
}
