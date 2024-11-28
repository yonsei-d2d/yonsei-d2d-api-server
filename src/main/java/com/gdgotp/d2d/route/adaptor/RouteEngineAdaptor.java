package com.gdgotp.d2d.route.adaptor;

import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.route.model.Route;

import java.util.List;

public interface RouteEngineAdaptor {
    public Route route(Routable origin, Routable destination);
    public Route route(Location origin, Location destination, List<Location> waypoints);
}
