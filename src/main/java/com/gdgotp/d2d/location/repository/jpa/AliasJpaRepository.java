package com.gdgotp.d2d.location.repository.jpa;

import com.gdgotp.d2d.location.entity.AliasEntity;
import com.gdgotp.d2d.location.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AliasJpaRepository extends JpaRepository<AliasEntity, Long> {
    List<AliasEntity> searchByNameContaining(String query);
}
