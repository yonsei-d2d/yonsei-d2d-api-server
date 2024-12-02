package com.gdgotp.d2d.route.model;

import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.model.Location;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Route {
    private List<Routable> path;
    private double duration;
    private double distance;
    private List<Long> nodes;
    private List<Location> waypoints;
    private List<Location> stopover;
    private List<String> guide;
}
