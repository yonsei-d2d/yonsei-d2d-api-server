package com.gdgotp.d2d.location.repository.jpa;

import com.gdgotp.d2d.location.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, UUID> {
    List<LocationEntity> findAllByType(String type);
    Optional<LocationEntity> findByName(String name);
    List<LocationEntity> findByNodeIdInAndType(List<Long> ids, String type);

    @Query(value = "SELECT l FROM location AS l WHERE l.type = :type ORDER BY ST_Distance(l.geom, ST_GeomFromText(:linestring, 4326)) ASC LIMIT 10")
    List<LocationEntity> findNearestFromLineStringByType(@Param("linestring") String linestring, @Param("type") String type);

    @Query(value = "SELECT DISTINCT l FROM location l JOIN FETCH l.tag t WHERE t.tag = :tag")
    List<LocationEntity> findLocationByTag( @Param("tag") String tag);
}
