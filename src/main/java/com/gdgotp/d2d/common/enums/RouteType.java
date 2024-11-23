package com.gdgotp.d2d.common.enums;

public enum RouteType {
    COORDINATES("coordinates"),
    ROOM("room"),
    LOCATION_ID("location_id");

    private final String type;

    RouteType(final String type) {
        this.type = type;
    }
}
