package com.gdgotp.d2d.assistant.service;

import com.gdgotp.d2d.assistant.dto.*;
import com.gdgotp.d2d.assistant.model.RouteRunState;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.location.service.LocationService;
import com.gdgotp.d2d.route.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AssistantService {

    private final RouteRunState runState;
    private final LocationService locationService;
    private final RouteService routeService;

    public AssistantService(RouteRunState runState, LocationService locationService, RouteService routeService) {
        this.runState = runState;
        this.locationService = locationService;
        this.routeService = routeService;
    }

    public List<Location> findLocationByName(FindLocationByNameDto input) {
        System.out.println("findLocationByName");
        var result = locationService.findLocationByNameContaining(input.getName());
        result.forEach(runState::putLocation);
        return result.subList(0, Math.min(3, result.size()));
    }

    public List<Location> findLocationByTag(FindLocationByTagDto input) {
        System.out.println("findLocationByTag");
        var result = locationService.findAllLocationByTag(input.getTag());
        result.forEach(runState::putLocation);
        return result.subList(0, Math.min(10, result.size()));
    }

    public List<String> generateRoute(GenerateRouteDto input) {
        System.out.println("generateRoute");
        System.out.println("input: " + input.toString());
        Location origin = runState.getLocation(input.getOriginId());
        Location destination = runState.getLocation(input.getDestinationId());
        List<Location> waypoints = input.getWaypointIds().stream().map(runState::getLocation).toList();
        runState.setRouteResult(routeService.routeWithWaypoints(origin, destination, waypoints));
        List<String> result = new ArrayList<>();
        result.add(origin.getName());
        for (Location location : waypoints) {
            result.add(location.getName());
        }
        result.add(destination.getName());
        return result;
    }

    public String reportError(ReportErrorDto input) {
        var result = input.getErrorMessage();
        runState.setError(result);
        return "reported";
    }

    public String setMode(SetModeDto input) {
        if (input.getMode().equalsIgnoreCase("route")) {
            runState.setRoute(true);
            System.out.println("Map Engine is now on ROUTE mode");
            return "Map Engine is now on ROUTE mode";
        } else if (input.getMode().equalsIgnoreCase("marker")) {
            runState.setMarker(true);
            System.out.println("Map Engine is now on MARKER mode");
            return "Map Engine is now on MARKER mode";
        }
        return "ERROR";
    }

    public String setMarker(SetMarkerDto input) {
        runState.setTargetLocation(locationService.findLocationById(input.getLocationId()));
        return "location id set completed";
    }
}
