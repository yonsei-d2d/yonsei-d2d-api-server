package com.gdgotp.d2d.route.service;

import com.gdgotp.d2d.route.dto.RouteRequestDto;
import com.gdgotp.d2d.route.dto.RouteResponseDto;

public interface RouteService {
    public RouteResponseDto route(RouteRequestDto routeRequestDto);
}
