package com.gdgotp.d2d.route.dto;

import com.gdgotp.d2d.common.enums.RouteType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RouteRequestDto {
    @NotEmpty
    private RouteLocationDto origin;
    @NotEmpty
    private RouteLocationDto destination;
    private RouteOptionDto options;

    @Getter
    @NoArgsConstructor
    public static class RouteLocationDto {
        @NotEmpty
        private RouteType routeType;
        private Long latitude;
        private Long longitude;
        private String locationId;
        private String room;
    }

    @Getter
    @NoArgsConstructor
    public static class RouteOptionDto {
        private Boolean excludeSteps;
        private Boolean excludeIndoor;
    }
}
