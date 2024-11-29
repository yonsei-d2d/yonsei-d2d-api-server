package com.gdgotp.d2d.location.repository;

import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.entity.LocationEntity;
import com.gdgotp.d2d.location.repository.jpa.LocationJpaRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LocationRepositoryImpl implements LocationRepository{
    private final LocationJpaRepository repository;


    public LocationRepositoryImpl(LocationJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LocationEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<LocationEntity> findAllByType(LocationType type) {
        return repository.findAllByType(type.toString());
    }

    @Override
    public Optional<LocationEntity> findById(String id) {
        return repository.findById(UUID.fromString(id));
    }

    @Override
    public List<LocationEntity> findByNodeIdInWithType(List<Long> ids, LocationType type) {
        return repository.findByNodeIdInAndType(ids, type.getValue());
    }

    @Override
    public Optional<LocationEntity> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<LocationEntity> findNearestFromRoutePathByType(List<Routable> path, LocationType type) {
        String linestring = routableToLinestring(path);
        return repository.findNearestFromLineStringByType(linestring, type.getValue());
    }

    @Override
    public List<LocationEntity> findByTag_Tag(String tag) {
        return repository.findLocationByTag(tag);
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
