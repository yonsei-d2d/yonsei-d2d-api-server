package com.gdgotp.d2d.location.repository.jpa;

import com.gdgotp.d2d.location.entity.LocationEntity;
import com.gdgotp.d2d.location.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagJpaRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findAllByTag(String tag);

    @Query(value = "SELECT l.id, " +
            "l.latitude, " +
            "l.longitude, " +
            "l.name, " +
            "l.floor, " +
            "l.node_id, " +
            "l.level, " +
            "l.type, " +
            "t2.id, " +
            "t2.tag " +
            "FROM tag t " +
            "LEFT JOIN location l ON t.location_id = l.id " +
            "LEFT JOIN tag t2 ON l.id = t2.location_id " +
            "WHERE t.tag = :tag " +
            "ORDER BY ST_Distance(l.geom, ST_GeomFromText(:linestring, 4326)) ASC LIMIT 15", nativeQuery = true)
    List<Object[]> findNearestFromLineStringByTag(@Param("linestring") String linestring, @Param("tag") String tag);
}
