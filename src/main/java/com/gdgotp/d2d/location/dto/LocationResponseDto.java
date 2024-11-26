package com.gdgotp.d2d.location.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationResponseDto {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private String type;
}
