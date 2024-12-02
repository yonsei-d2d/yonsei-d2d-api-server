package com.gdgotp.d2d.location.service;

import com.gdgotp.d2d.common.enums.LocationType;
import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.location.dto.LocationResponseDto;
import com.gdgotp.d2d.location.mapper.LocationMapper;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.location.repository.AliasRepository;
import com.gdgotp.d2d.location.repository.LocationRepository;
import com.gdgotp.d2d.location.repository.TagRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final AliasRepository aliasRepository;
    private final TagRepository tagRepository;

    public LocationServiceImpl(LocationRepository locationRepository, AliasRepository aliasRepository, TagRepository tagRepository) {
        this.locationRepository = locationRepository;
        this.aliasRepository = aliasRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Location> findAllLocationByType(LocationType locationType) {
        var result = locationRepository.findAllByType(locationType);
        return result.stream().map(LocationMapper::fromEntity).toList();
    }

    @Override
    public Location findLocationById(String id) {
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
        var result = findLocationByNameContaining(query);
        return result.stream().map(LocationMapper::toLocationResponseDto).toList();
    }

    @Override
    public List<Location> findLocationByNameContaining(String query) {
        var result = aliasRepository.searchByNameContaining(query)
                .stream()
                .map(LocationMapper::fromAliasEntity)
                .toList();;
        return result;
    }

    @Override
    public List<Location> findNearestLocationFromRoutePathByTag(List<Routable> path, String tag) {
        var result = tagRepository.findNearestLocationFromRoutePathByTag(path, tag);
        return result.stream().map(LocationMapper::fromEntity).toList();
    }

    @Override
    public List<Location> findAllLocationByTag(String tag) {
        var result = locationRepository.findByTag_Tag(tag);
        return result.stream().map(LocationMapper::fromEntity).toList();
    }
}
