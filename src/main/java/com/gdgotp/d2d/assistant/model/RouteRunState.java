package com.gdgotp.d2d.assistant.model;

import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.route.model.Route;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;

@Component
@RequestScope
@Getter
@Setter
public class RouteRunState {
    private boolean isRoute;
    private boolean isMarker;
    private HashMap<String, Location> locations = new HashMap<>();
    private Location targetLocation;
    private Route routeResult;
    private String error;

    public void putLocation(Location l) {
        locations.put(l.getId(), l);
    }

    public Location getLocation(String id) {
        return locations.get(id);
    }
}
