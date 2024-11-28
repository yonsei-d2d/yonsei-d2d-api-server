package com.gdgotp.d2d.location.repository;

import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.entity.LocationEntity;

import java.util.List;
import java.util.Optional;

public interface LocationRepository {
    public List<LocationEntity> findAll();
    public Optional<LocationEntity> findByName(String name);
    public List<LocationEntity> findAllByType(LocationType type);
    public Optional<LocationEntity> findById(Long id);
    public List<LocationEntity> findByNodeIdInWithType(List<Long> ids, LocationType type);
    public List<LocationEntity> findNearestFromRoutePathByType(List<Routable> path, LocationType type);
}
