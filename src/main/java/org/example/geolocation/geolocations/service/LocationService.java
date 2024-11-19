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

    public List<LocationDto> getAllPublicLocations(LocationDto locationDto) {

        return locationRepository.findAll().stream()
                .filter(location -> location.getIsPublic().equals(true))
                .map(LocationDto::convertToDto)
                .toList();
    }

    public LocationDto getPublicLocationById(Integer id) {
        return locationRepository.findByIdAndIsPublic(id, true)
                .map(LocationDto::convertToDto)
                .orElseThrow(() -> new IllegalArgumentException("Location with id " + id + " does not exist or is not public"));
    }

}
