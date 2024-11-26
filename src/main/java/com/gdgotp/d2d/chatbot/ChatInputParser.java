package com.gdgotp.d2d.chatbot;

import java.util.regex.*;

public class ChatInputParser {

    /**
     * Parses the user's input to extract origin, destination, and any waypoint request.
     * @param userInput The user's natural language input.
     * @return ParsedInput containing origin, destination, and waypoint request.
     */
    public static ParsedInput parseInput(String userInput) {
        String locationPattern = "([가-힣A-Za-z]+)";
        String waypointPattern = "(사진|카페|화장실|레스토랑|chill|photo|restroom|cafe|restaurant)";

        Pattern originDestPattern = Pattern.compile(
            String.format("^(.*)%s에서(.*)%s까지.*", locationPattern, locationPattern)
        );
        Matcher matcher = originDestPattern.matcher(userInput);

        String originBuilding = null, destinationBuilding = null, waypoint = null;
        if (matcher.find()) {
            originBuilding = matcher.group(2).trim();
            destinationBuilding = matcher.group(4).trim();
        }

        Pattern waypointPatternCompiled = Pattern.compile(waypointPattern);
        Matcher waypointMatcher = waypointPatternCompiled.matcher(userInput);
        if (waypointMatcher.find()) {
            waypoint = mapWaypointTag(waypointMatcher.group());
        }

        if (originBuilding == null || destinationBuilding == null) {
            throw new IllegalArgumentException("Could not parse origin and destination.");
        }

        return new ParsedInput(originBuilding, destinationBuilding, waypoint);
    }

    private static String mapWaypointTag(String userTag) {
        return switch (userTag) {
            case "사진", "photo" -> "photo";
            case "카페", "cafe" -> "cafe";
            case "화장실", "restroom" -> "restroom";
            case "레스토랑", "restaurant" -> "restaurant";
            default -> null;
        };
    }

    public static record ParsedInput(String origin, String destination, String waypoint) {}
}
