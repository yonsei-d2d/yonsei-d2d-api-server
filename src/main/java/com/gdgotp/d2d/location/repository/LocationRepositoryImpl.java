package com.gdgotp.d2d.location.repository;

import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.location.entity.LocationEntity;
import com.gdgotp.d2d.location.repository.jpa.LocationJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<LocationEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<LocationEntity> findByNodeIdInWithType(List<Long> ids, LocationType type) {
        return repository.findByNodeIdInAndType(ids, type.getValue());
    }

    @Override
    public Optional<LocationEntity> findByName(String name) {
        return repository.findByName(name);
    }
}
