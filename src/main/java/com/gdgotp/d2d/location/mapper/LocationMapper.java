package com.gdgotp.d2d.location.mapper;

import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.location.dto.LocationResponseDto;
import com.gdgotp.d2d.location.entity.AliasEntity;
import com.gdgotp.d2d.location.entity.LocationEntity;
import com.gdgotp.d2d.location.entity.TagEntity;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.route.dto.RouteResponseDto;

public class LocationMapper {
    public static Location fromEntity(LocationEntity entity) {
        return Location.builder()
                .id(entity.getId().toString())
                .lat(entity.getLatitude())
                .lng(entity.getLongitude())
                .nodeId(entity.getNodeId())
                .name(entity.getName())
                .level(entity.getLevel())
                .tag(entity.getTag().stream().map(TagEntity::getTag).toList())
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

    public static LocationResponseDto toLocationResponseDto(Location location) {
        return LocationResponseDto.builder()
                .id(location.getId())
                .latitude(location.getLat())
                .longitude(location.getLng())
                .name(location.getName())
                .type(location.getType().getValue())
                .build();
    }

    public static Location fromAliasEntity(AliasEntity entity) {
        var location = entity.getLocation();
        return Location.builder()
                .id(location.getId().toString())
                .lat(location.getLatitude())
                .lng(location.getLongitude())
                .nodeId(location.getNodeId())
                .name(location.getName())
                .level(location.getLevel())
                .type(LocationType.fromValue(location.getType()))
                .build();
    }


}
