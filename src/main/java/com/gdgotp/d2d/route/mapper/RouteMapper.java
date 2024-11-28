package com.gdgotp.d2d.route.mapper;

import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.mapper.LocationMapper;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.route.dto.RouteResponseDto;
import com.gdgotp.d2d.route.model.Route;

import java.util.List;
import java.util.stream.Collectors;

public class RouteMapper {
    public static Route fromRouteEngineResult(com.gdgotp.d2d.infra.osrm.types.Route route) {
        return Route.builder()
                .distance(route.getDistance())
                .duration(route.getDuration())
                .path(route.getGeometry()
                        .getCoordinates()
                        .stream()
                        .map(e -> (Routable) Routable.builder()
                                .lat(e.get(1))
                                .lng(e.get(0))
                                .build())
                        .toList())
                .nodes(route.getLegs().get(0).getAnnotation().getNodes())
                .build();
    }

    public static RouteResponseDto toRouteResponseDto(Route route) {
        return RouteResponseDto.builder()
                .path(route.getPath())
                .duration(route.getDuration())
                .distance(route.getDistance())
                .waypoints(route.getWaypoints().stream().map(LocationMapper::toWaypointDto).toList())
                .guide(route.getGuide())
                .build();
    }
}
