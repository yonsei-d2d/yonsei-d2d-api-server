package com.gdgotp.d2d.location.mapper;

import com.gdgotp.d2d.location.entity.LocationEntity;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.route.dto.RouteResponseDto;

public class LocationMapper {
    public static Location fromEntity(LocationEntity entity) {
        return Location.builder()
                .lat(entity.getLatitude())
                .lng(entity.getLongitude())
                .nodeId(entity.getNodeId())
                .name(entity.getName())
                .level(entity.getLevel())
                .build();
    }

    public static RouteResponseDto.WaypointDto toWaypointDto(Location location) {
        return RouteResponseDto.WaypointDto.builder()
                .lat(location.getLat())
                .lng(location.getLng())
                .name(location.getName())
                .level(location.getLevel())
                .build();
    }
}
