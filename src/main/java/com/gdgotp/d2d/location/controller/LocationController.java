package com.gdgotp.d2d.location.controller;

import com.gdgotp.d2d.location.dto.LocationResponseDto;
import com.gdgotp.d2d.location.dto.SearchLocationRequestDto;
import com.gdgotp.d2d.location.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }



    @PostMapping("search")
    @ResponseBody
    public List<LocationResponseDto> search(@RequestBody SearchLocationRequestDto request) {
        return locationService.searchLocation(request.getQuery());
    }
}
