package org.example.geolocation.geolocations.service;

import org.example.geolocation.geolocations.dto.CategoryDto;
import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    LocationRepository locationRepository;
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationDto> allLocations(LocationDto locationDto) {

        return locationRepository.findAll().stream()
                .filter(location -> location.getIsPublic().equals(true))
                .map(LocationDto::convertToDto)
                .toList();
    }

    public Optional<LocationDto> getLocationById(Integer id) {
        return locationRepository.findById(id).map(LocationDto::convertToDto);
    }

}
