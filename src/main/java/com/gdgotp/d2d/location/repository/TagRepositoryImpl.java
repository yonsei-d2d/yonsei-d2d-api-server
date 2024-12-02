package com.gdgotp.d2d.location.repository;


import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.entity.AliasEntity;
import com.gdgotp.d2d.location.entity.LocationEntity;
import com.gdgotp.d2d.location.entity.TagEntity;
import com.gdgotp.d2d.location.mapper.LocationMapper;
import com.gdgotp.d2d.location.repository.jpa.AliasJpaRepository;
import com.gdgotp.d2d.location.repository.jpa.TagJpaRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class TagRepositoryImpl implements TagRepository{
    private final TagJpaRepository repository;

    public TagRepositoryImpl(TagJpaRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<TagEntity> findAllByTag(String tag) {
        return repository.findAllByTag(tag);
    }

    @Override
    public List<LocationEntity> findNearestLocationFromRoutePathByTag(List<Routable> path, String tag) {
        return this.repository.findNearestFromLineStringByTag(routableToLinestring(path), tag);
    }

    private String routableToLinestring(List<Routable> path) {
        GeometryFactory geometryFactory = new GeometryFactory();

        // Transform Routable (lat,lng) to Coordinates
        var coordinatesList = path.stream().map(e -> new Coordinate(e.getLng(), e.getLat())).toArray(Coordinate[]::new);
        LineString lineString = geometryFactory.createLineString(coordinatesList);

        // Transform Coordinates to Linestring
        WKTWriter writer = new WKTWriter();
        return writer.write(lineString);
    }
}
