package com.gdgotp.d2d.location.repository.jpa;

import com.gdgotp.d2d.location.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, Long> {
    List<LocationEntity> findAllByType(String type);
    Optional<LocationEntity> findByName(String name);
    List<LocationEntity> findByNodeIdInAndType(List<Long> ids, String type);

    @Query(value = "SELECT * FROM location WHERE location.type = :type ORDER BY ST_Distance(location.geom, ST_GeomFromText(:linestring, 4326)) ASC LIMIT 10", nativeQuery = true)
    List<LocationEntity> findNearestFromLineStringByType(@Param("linestring") String linestring, @Param("type") String type);
}
