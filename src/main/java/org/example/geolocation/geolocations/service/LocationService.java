package org.example.geolocation.geolocations.service;

import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    LocationRepository locationRepository;
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationDto> allLocations() {
        return locationRepository.findAll().stream()
                .map(LocationDto::convertToDto).toList();
    }
}
