package com.gdgotp.d2d.location.repository;


import com.gdgotp.d2d.location.entity.AliasEntity;
import com.gdgotp.d2d.location.repository.jpa.AliasJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AliasRepositoryImpl implements AliasRepository{
    private final AliasJpaRepository repository;

    public AliasRepositoryImpl(AliasJpaRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<AliasEntity> searchByNameContaining(String name) {
        return repository.searchByNameContaining(name);
    }
}
