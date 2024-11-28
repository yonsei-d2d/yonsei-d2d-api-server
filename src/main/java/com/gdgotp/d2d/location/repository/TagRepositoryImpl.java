package com.gdgotp.d2d.location.repository;


import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.entity.AliasEntity;
import com.gdgotp.d2d.location.entity.LocationEntity;
import com.gdgotp.d2d.location.entity.TagEntity;
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

    public List<LocationEntity> findNearestLocationFromRoutePathByTag(List<Routable> path, String tag) {
        String linestring = routableToLinestring(path);
        var temp = repository.findNearestFromLineStringByTag(linestring, tag);
        HashMap<String, LocationEntity> locations = new HashMap<>();
        List<LocationEntity> nearestLocations = new ArrayList<>();
        for (Object[] row : temp) {
            System.out.println(
                    row[0].toString() +
                    row[1].toString() +
                    row[2].toString() +
                    row[3].toString() +
                    row[5].toString() +
                    row[6].toString() +
                    row[7].toString() +
                    row[8].toString() +
                    row[9].toString()
            );
            UUID uuid = (UUID) row[0];
            if (locations.containsKey(uuid.toString())) {
                TagEntity newTag = new TagEntity();
                newTag.setId((Long) row[8]);
                newTag.setTag((String) row[9]);
                LocationEntity l = locations.get(uuid.toString());
                l.getTag().add(newTag);
            } else {
                var curLocation = new LocationEntity();
                curLocation.setId(uuid);
                curLocation.setLatitude((Double) row[1]);
                curLocation.setLongitude((Double) row[2]);
                curLocation.setName((String) row[3]);
                curLocation.setFloor((Integer) row[4]);
                curLocation.setNodeId((Long) row[5]);
                curLocation.setLevel((Integer) row[6]);
                curLocation.setType((String) row[7]);

                TagEntity newTag = new TagEntity();
                newTag.setId((Long) row[8]);
                newTag.setTag((String) row[9]);
                List<TagEntity> newTagList = new ArrayList<>();
                newTagList.add(newTag);
                curLocation.setTag(newTagList);

                nearestLocations.add(curLocation);
                locations.put(uuid.toString(), curLocation);
            }
        }
        return nearestLocations;

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
