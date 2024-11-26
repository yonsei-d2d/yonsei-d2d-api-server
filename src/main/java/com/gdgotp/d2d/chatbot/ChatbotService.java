package com.gdgotp.d2d.chatbot;

import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.location.repository.LocationRepository;
import com.gdgotp.d2d.route.dto.RouteRequestDto;
import com.gdgotp.d2d.route.dto.RouteResponseDto;
import com.gdgotp.d2d.route.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    private final LocationRepository locationRepository;
    private final RouteService routeService;

    public ChatbotService(LocationRepository locationRepository, RouteService routeService) {
        this.locationRepository = locationRepository;
        this.routeService = routeService;
    }

    /**
     * Processes the user's natural language input to generate a route.
     *
     * @param userInput The natural language input from the user.
     * @return A map containing the chatbot response and route data.
     */
    public Map<String, Object> processRoutingInput(String userInput) {
        // Parse user input
        ChatInputParser.ParsedInput parsedInput = ChatInputParser.parseInput(userInput);

        // Resolve origin and destination locations
        Location origin = resolveLocation(parsedInput.origin());
        Location destination = resolveLocation(parsedInput.destination());

        // Resolve waypoints (if requested)
        List<Location> waypoints = parsedInput.waypoint() != null
                ? resolveWaypoints(parsedInput.waypoint())
                : List.of();

        // Build route request
        RouteRequestDto routeRequest = new RouteRequestDto(
                new RouteRequestDto.RouteLocationDto(origin.getLat(), origin.getLng()),
                new RouteRequestDto.RouteLocationDto(destination.getLat(), destination.getLng()),
                waypoints.stream()
                        .map(wp -> new RouteRequestDto.RouteLocationDto(wp.getLat(), wp.getLng()))
                        .toList()
        );

        // Fetch route data
        RouteResponseDto routeResponse = routeService.route(routeRequest);

        // Generate chatbot response
        String chatbotMessage = generateChatbotResponse(routeResponse, origin, destination, waypoints);

        // Return chatbot response and route data
        Map<String, Object> response = new HashMap<>();
        response.put("chatbotResponse", chatbotMessage);
        response.put("routeData", routeResponse);
        return response;
    }

    /**
     * Resolves a building name into a Location object.
     *
     * @param buildingName The name of the building.
     * @return The corresponding Location object.
     */
    private Location resolveLocation(String buildingName) {
        return locationRepository.findByName(buildingName)
                .map(entity -> new Location(entity.getNodeId(), entity.getLatitude(), entity.getLongitude(), entity.getName()))
                .orElseThrow(() -> new IllegalArgumentException("Building not found: " + buildingName));
    }

    /**
     * Resolves waypoints based on a requested tag (e.g., "photo", "cafe").
     *
     * @param tag The waypoint type requested by the user.
     * @return A list of resolved Location objects.
     */
    private List<Location> resolveWaypoints(String tag) {
        return locationRepository.findAllByType(tag)
                .stream()
                .map(entity -> new Location(entity.getNodeId(), entity.getLatitude(), entity.getLongitude(), entity.getName()))
                .toList();
    }

    /**
     * Generates a chatbot-friendly response based on the route data.
     *
     * @param routeResponse The route response data.
     * @param origin         The origin location.
     * @param destination    The destination location.
     * @param waypoints      The list of waypoint locations (if any).
     * @return A natural language response for the chatbot.
     */
    private String generateChatbotResponse(RouteResponseDto routeResponse, Location origin, Location destination, List<Location> waypoints) {
        String waypointNames = waypoints.isEmpty()
                ? "no waypoints"
                : waypoints.stream()
                           .map(Location::getName)
                           .collect(Collectors.joining(", "));

        return String.format(
            "Start from %s. %sEnd at %s. The total distance is %.2f km and the estimated time is %d minutes.",
            origin.getName(),
            waypointNames.equals("no waypoints") ? "" : "Pass through " + waypointNames + ". ",
            destination.getName(),
            routeResponse.getDistance() / 1000.0, // Convert meters to km
            routeResponse.getDuration() / 60 // Convert seconds to minutes
        );
    }
}
