package com.gdgotp.d2d.route.adaptor;

import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.route.model.Route;

public interface RouteEngineAdaptor {
    public Route route(Routable origin, Routable destination);
}
