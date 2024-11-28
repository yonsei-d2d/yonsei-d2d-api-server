package com.gdgotp.d2d.route.dto;

import com.gdgotp.d2d.common.types.Routable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RouteResponseDto {
    private List<Routable> path;
    private double duration;
    private double distance;
    private List<WaypointDto> waypoints;
    private List<String> guide;

    @SuperBuilder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class WaypointDto extends Routable {
        private String name;
        private Integer level;
    }
}
