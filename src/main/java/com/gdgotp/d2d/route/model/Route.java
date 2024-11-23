package com.gdgotp.d2d.route.model;

import com.gdgotp.d2d.location.model.Location;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Route {
    private String path;
    private double duration;
    private double distance;
    private List<Long> nodes;
    private List<Location> waypoints;
    private List<String> guide;
}
