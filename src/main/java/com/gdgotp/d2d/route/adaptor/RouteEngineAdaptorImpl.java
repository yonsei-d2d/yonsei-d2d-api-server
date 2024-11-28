package com.gdgotp.d2d.route.adaptor;

import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.infra.osrm.OsrmClient;
import com.gdgotp.d2d.infra.osrm.types.OsrmRouteResult;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.route.mapper.RouteMapper;
import com.gdgotp.d2d.route.model.Route;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteEngineAdaptorImpl implements RouteEngineAdaptor {

    private final OsrmClient osrmClient;

    public RouteEngineAdaptorImpl(OsrmClient osrmClient) {
        this.osrmClient = osrmClient;
    }

    @Override
    public Route route(Routable origin, Routable destination) {
        OsrmRouteResult osrmResult = osrmClient.route(origin.getLat(), origin.getLng(), destination.getLat(), destination.getLng());
        var selectedRoute = osrmResult.getRoutes().get(0);
        return RouteMapper.fromRouteEngineResult(selectedRoute);
    }

    @Override
    public Route route(Location origin, Location destination, List<Location> waypoints) {
        List<Routable> routableWaypoint = waypoints.stream().map(e -> {
            return (Routable) Routable.builder()
                    .lng(e.getLng())
                    .lat(e.getLat())
                    .build();
        }).toList();
        OsrmRouteResult osrmResult = osrmClient.routeV2(origin, destination, routableWaypoint);
        var selectedRoute = osrmResult.getRoutes().get(0);
        return RouteMapper.fromRouteEngineResult(selectedRoute);
    }
}
