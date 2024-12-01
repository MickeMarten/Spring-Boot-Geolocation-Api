package org.example.geolocation.geolocations.service;

import org.example.geolocation.geolocations.configuration.ApplicationConfig;
import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.entity.Category;
import org.example.geolocation.geolocations.entity.Location;
import org.example.geolocation.geolocations.repository.CategoryRepository;
import org.example.geolocation.geolocations.repository.LocationRepository;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

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

    public List<LocationDto> getLocationWithinArea(Point center, double radius) {

        return locationRepository.findAllWithinRadius(center, radius).stream()
                .map(LocationDto::convertToDto)
                .toList();


    }

    public List<LocationDto> getLocationsForUser(Principal principal) {
        String username = principal.getName();

        List<Location> locations = locationRepository.findByUser(username);

        return locations.stream()
                .map(LocationDto::convertToDto)
                .toList();
    }


    public Integer addLocation(LocationDto locationDto, Principal principal) {

        Optional<Location> existingLocation = locationRepository.findByName(locationDto.name());
        if (existingLocation.isPresent()) {
            throw new IllegalArgumentException("Location with name '" + locationDto.name() + "' already exists");
        }

        Category categoryId = categoryRepository.findById(locationDto.category_id())
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + locationDto.category_id() + " does not exist"));

        Location newLocation = new Location();
        newLocation.setName(locationDto.name());
        newLocation.setCategory(categoryId);
        newLocation.setDescription(locationDto.description());
        newLocation.setCoordinate(locationDto.coordinate());
        newLocation.setUser(principal.getName());
        locationRepository.save(newLocation);
        return newLocation.getId();
    }


    public void updateLocation(LocationDto updatedLocationDto, Integer locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location with id " + locationId + " not found."));

        Category category = categoryRepository.findById(updatedLocationDto.category_id())
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + updatedLocationDto.category_id() + " not found."));

        location.setName(updatedLocationDto.name());
        location.setDescription(updatedLocationDto.description());
        location.setCoordinate(updatedLocationDto.coordinate());
        location.setCategory(category);
        locationRepository.save(location);

    }


    public void deleteLocation(Integer id) {

        locationRepository.deleteById(id);

    }


}
