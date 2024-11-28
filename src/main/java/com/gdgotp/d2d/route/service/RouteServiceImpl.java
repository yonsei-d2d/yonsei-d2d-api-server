package com.gdgotp.d2d.route.service;

import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.error.InvalidRouteLocationException;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.location.service.LocationService;
import com.gdgotp.d2d.route.adaptor.RouteEngineAdaptor;
import com.gdgotp.d2d.route.dto.RouteRequestDto;
import com.gdgotp.d2d.route.dto.RouteResponseDto;
import com.gdgotp.d2d.route.mapper.RouteMapper;
import com.gdgotp.d2d.route.model.Route;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteEngineAdaptor routeEngineAdaptor;
    private final LocationService locationService;
    private final RoomService roomService;

    public RouteServiceImpl(RouteEngineAdaptor routeEngineAdaptor, LocationService locationService, RoomService roomService) {
        this.routeEngineAdaptor = routeEngineAdaptor;
        this.locationService = locationService;
        this.roomService = roomService;
    }

    private void isValidRouteLocation(RouteRequestDto.RouteLocationDto input) {
        switch (input.getRouteType()) {
            case LOCATION_ID:
                if (input.getLocationId() != null) return;
            case ROOM:
                if (input.getRoom() != null && !input.getRoom().isBlank()) return;
            case COORDINATES:
                if (input.getLatitude() != null && input.getLongitude() != null) return;
            default:
                throw new InvalidRouteLocationException();
        }
    }

    private Location mapRouteLocation(RouteRequestDto.RouteLocationDto input) {
        return switch (input.getRouteType()) {
            case LOCATION_ID -> locationService.findLocationById(input.getLocationId());
            case ROOM -> roomService.findLocationByRoom(input.getRoom());
            case COORDINATES -> Location.builder()
                    .lat(input.getLatitude())
                    .lng(input.getLongitude())
                    .level(0)
                    .build();
        };
    }

    private List<Location> mapWaypoints(List<Long> waypointNodeIds) {
        // Get waypoints
        Map<Long, Location> waypointMap = locationService.findWaypointLocationByNodeIdIn(waypointNodeIds)
                .stream()
                .collect(Collectors.toMap(Location::getNodeId, e->e));

        // Sort and map waypoints
        return new ArrayList<>(waypointNodeIds
                .stream()
                .map(waypointMap::get)
                .filter(Objects::nonNull)
                .toList());
    }

    private List<String> generateGuide(List<Location> waypoints) {
        int currentLevel = waypoints.get(0).getLevel();
        List<String> guides = new ArrayList<>();
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Location cur = waypoints.get(i);
            Location next = waypoints.get(i + 1);

            int nextLevel = next.getLevel();

            // Ignore Level 0
            if (nextLevel == 0) nextLevel = currentLevel;
            if (currentLevel == 0) currentLevel = nextLevel;

            String originName = cur.getName();
            String destinationName = next.getName();

            // Shrink same name waypoint
            if (originName.equals(destinationName)) {
                guides.add("");
                continue;
            }

            // Build Guide
            if (currentLevel != nextLevel) {
                String currentLevelString = String.valueOf(currentLevel).replaceAll("-", "지하");
                String nextLevelString = String.valueOf(nextLevel).replaceAll("-", "지하");

                String guide = originName +
                        " (" +
                        currentLevelString +
                        "층)에서 " +
                        destinationName +
                        " (" +
                        nextLevelString +
                        "층)으로 이동.";

                guides.add(guide);

                currentLevel = nextLevel;
            } else {
                String guide = originName +
                        "에서 " +
                        destinationName +
                        "까지 도보로 이동.";
                guides.add(guide);
            }
        }
        guides.add("");
        return guides;
    }

    @Override
    public RouteResponseDto routeByDto(RouteRequestDto input) {
        var originInput = input.getOrigin();
        var destinationInput = input.getDestination();

        // Validate route input
        isValidRouteLocation(originInput);
        isValidRouteLocation(destinationInput);

        // Map to location
        Location origin = mapRouteLocation(originInput);
        Location destination = mapRouteLocation(destinationInput);
        if (origin == null || destination == null) throw new InvalidRouteLocationException();

        // OSRM Route
        Route result = route(origin, destination);

        // Map waypoints
        List<Location> waypoints = new ArrayList<>();
        waypoints.add(origin);
        waypoints.addAll(mapWaypoints(result.getNodes()));
        waypoints.add(destination);

        // Generate Text Guide
        List<String> guide = generateGuide(waypoints);

        result.setWaypoints(List.of(origin, destination));
        result.setGuide(guide);
        return RouteMapper.toRouteResponseDto(result);
    }

    @Override
    public Route route(Routable origin, Routable destination) {
        return routeEngineAdaptor.route(origin, destination);
    }


    @Override
    public Route routeWithWaypoints(Location origin, Location destination, List<Location> way) {
        Route result = routeEngineAdaptor.route(origin, destination, way);
        // Map waypoints
        List<Location> waypoints = new ArrayList<>();
        waypoints.add(origin);
        waypoints.addAll(way);
        waypoints.add(destination);

        // Generate Text Guide
        List<String> guide = generateGuide(waypoints);

        result.setWaypoints(waypoints);
        result.setGuide(guide);
        return result;
    }
}
