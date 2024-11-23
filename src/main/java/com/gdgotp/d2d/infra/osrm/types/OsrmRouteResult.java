package com.gdgotp.d2d.infra.osrm.types;

import lombok.Getter;

import java.util.List;

@Getter
public class OsrmRouteResult {
    private String code;
    private List<Route> routes;
    private List<Waypoint> waypoints;
}
