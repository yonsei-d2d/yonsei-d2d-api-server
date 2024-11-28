package com.gdgotp.d2d.location.service;

import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.dto.LocationResponseDto;
import com.gdgotp.d2d.location.model.Location;

import java.util.List;

public interface LocationService {
    public List<Location> findAllLocationByType(LocationType locationType);
    public List<Location> findWaypointLocationByNodeIdIn(List<Long> ids);
    public Location findLocationById(long id);
    public Location findLocationByName(String name);
    public List<LocationResponseDto> searchLocation(String name);
    public List<Location> findNearestLocationFromRoutePathByType(List<Routable> path);
}
