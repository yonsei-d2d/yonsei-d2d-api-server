package com.gdgotp.d2d.location.service;

import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.location.dto.LocationResponseDto;
import com.gdgotp.d2d.location.mapper.LocationMapper;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.location.repository.AliasRepository;
import com.gdgotp.d2d.location.repository.LocationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final AliasRepository aliasRepository;

    public LocationServiceImpl(LocationRepository locationRepository, AliasRepository aliasRepository) {
        this.locationRepository = locationRepository;
        this.aliasRepository = aliasRepository;
    }

    @Override
    public List<Location> findAllLocationByType(LocationType locationType) {
        var result = locationRepository.findAllByType(locationType);
        return result.stream().map(LocationMapper::fromEntity).toList();
    }

    @Override
    public Location findLocationById(long id) {
        var result = locationRepository.findById(id);
        return result.map(LocationMapper::fromEntity).orElse(null);
    }

    @Override
    public List<Location> findWaypointLocationByNodeIdIn(List<Long> ids) {
        var result = locationRepository.findByNodeIdInWithType(ids, LocationType.WAYPOINT);
        return result.stream().map(LocationMapper::fromEntity).toList();
    }

    @Override
    public Location findLocationByName(String name) {
        var result = locationRepository.findByName(name);
        return result.map(LocationMapper::fromEntity).orElse(null);
    }

    @Override
    public List<LocationResponseDto> searchLocation(String query) {
        var result = aliasRepository.searchByNameContaining(query)
                .stream()
                .map(LocationMapper::fromAliasEntity)
                .toList();;
        return result.stream().map(LocationMapper::toLocationResponseDto).toList();
    }
}
