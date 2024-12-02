package com.gdgotp.d2d.route.service;

import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.route.dto.RouteRequestDto;
import com.gdgotp.d2d.route.dto.RouteResponseDto;
import com.gdgotp.d2d.route.model.Route;

import java.util.List;

public interface RouteService {
    public RouteResponseDto routeByDto(RouteRequestDto routeRequestDto);
    public Route route(Routable origin, Routable destination);
    public Route routeWithWaypoints(Location origin, Location destination, List<Location> way);
}
