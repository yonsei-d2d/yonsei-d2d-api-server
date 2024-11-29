package com.gdgotp.d2d.location.repository.jpa;

import com.gdgotp.d2d.location.entity.LocationEntity;
import com.gdgotp.d2d.location.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagJpaRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findAllByTag(String tag);

    @Query(value = "SELECT l, t from tag AS t " +
            "JOIN FETCH t.location AS l " +
            "WHERE t.tag = :tag " +
            "ORDER BY ST_Distance(l.geom, ST_GeomFromText(:linestring, 4326)) ASC LIMIT 15")
    List<LocationEntity> findNearestFromLineStringByTag(@Param("linestring") String linestring, @Param("tag") String tag);
}
