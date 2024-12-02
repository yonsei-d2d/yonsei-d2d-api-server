package com.gdgotp.d2d.assistant.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
@JsonClassDescription("You can generate route by location id.")
public class GenerateRouteDto {
    @JsonPropertyDescription("location id of origin")
    private String originId;
    @JsonPropertyDescription("location id of waypoints. must be entered in the requested order")
    private List<String> waypointIds;
    @JsonPropertyDescription("location id of destination")
    private String destinationId;
}
