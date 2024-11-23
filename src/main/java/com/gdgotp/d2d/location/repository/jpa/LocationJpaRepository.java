package com.gdgotp.d2d.location.repository.jpa;

import com.gdgotp.d2d.location.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, Long> {
    List<LocationEntity> findAllByType(String type);
    Optional<LocationEntity> findByName(String name);
    List<LocationEntity> findByNodeIdInAndType(List<Long> ids, String type);
}
