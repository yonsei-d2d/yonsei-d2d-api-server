package com.gdgotp.d2d.location.repository;

import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.location.entity.AliasEntity;
import com.gdgotp.d2d.location.entity.LocationEntity;

import java.util.List;
import java.util.Optional;

public interface AliasRepository {
    public List<AliasEntity> searchByNameContaining(String name);
}
