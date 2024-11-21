package org.example.geolocation.geolocations.service;

import org.example.geolocation.geolocations.dto.CategoryDto;
import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.entity.Category;
import org.example.geolocation.geolocations.entity.Location;
import org.example.geolocation.geolocations.repository.CategoryRepository;
import org.example.geolocation.geolocations.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodType;
import java.util.List;
import java.util.Optional;
import java.util.logging.Filter;

@Service
public class LocationService {


    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public LocationService(CategoryRepository categoryRepository, LocationRepository locationRepository) {
        this.categoryRepository = categoryRepository;
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

    public List<LocationDto> getPublicLocationsByCategory(Integer categoryId) {
        return locationRepository.findByCategoryIdAndIsPublic(categoryId, true)
                .stream()
                .map(LocationDto::convertToDto)
                .toList();


    }


    public Integer addLocation(LocationDto locationDto) {

        Category categoryId = categoryRepository.findById(locationDto.category_id())
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + locationDto.category_id() + " does not exist"));

        Location newLocation = new Location();
        newLocation.setName(locationDto.name());
        newLocation.setCategory(categoryId);
        newLocation.setDescription(locationDto.description());
        newLocation.setCoordinate(locationDto.coordinate());
        locationRepository.save(newLocation);
        return newLocation.getId();
    }

    public void deleteLocation(Integer id) {

        locationRepository.deleteById(id);

    }



}
