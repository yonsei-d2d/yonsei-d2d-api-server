package com.gdgotp.d2d.location.repository;

import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.entity.AliasEntity;
import com.gdgotp.d2d.location.entity.LocationEntity;
import com.gdgotp.d2d.location.entity.TagEntity;
import com.gdgotp.d2d.location.model.Location;

import java.util.List;

public interface TagRepository {
    public List<TagEntity> findAllByTag(String tag);
    public List<LocationEntity> findNearestLocationFromRoutePathByTag(List<Routable> path, String tag);
}
