package com.gdgotp.d2d.route.controller;

import com.gdgotp.d2d.route.dto.*;
import com.gdgotp.d2d.route.service.RouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping()
    @ResponseBody
    public RouteResponseDto route(@RequestBody RouteRequestDto input) {
        return routeService.routeByDto(input);
    }
}
