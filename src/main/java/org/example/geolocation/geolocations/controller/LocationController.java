package org.example.geolocation.geolocations.controller;

import org.example.geolocation.geolocations.center.Center;
import org.example.geolocation.geolocations.dto.CategoryDto;
import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.entity.Category;
import org.example.geolocation.geolocations.entity.Location;
import org.example.geolocation.geolocations.service.LocationService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class LocationController {

    LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations/public")
    public List<LocationDto> publicLocations(LocationDto locationDto) {
        return locationService.getAllPublicLocations(locationDto);

        // Hämta alla publica platser

    }

    @GetMapping("/locations/public/{locationId}")
    public LocationDto publicLocationId(@PathVariable("locationId") Integer locationId) {
        return locationService.getPublicLocationById(locationId);
        //en specifik publik plats (för anonyma användare).
    }

    @GetMapping("/locations/public/category/{categoryId}")
    public ResponseEntity<List<LocationDto>> publicLocationCategoryId(@PathVariable("categoryId") Integer categoryId) {
        List<LocationDto> locations = locationService.getPublicLocationsByCategory(categoryId);
        if (locations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(locations);
        //Hämta alla publika platser inom en specifik kategori.
    }

    @GetMapping("locations/user")
    public String userLocation() {
        return "user connected with location";
        //Hämta alla platser (både publika och privata) som tillhör den
        //inloggade användaren.
    }



    @GetMapping("locations/public/area")
    public List<LocationDto> getLocationsWithinRadius(@RequestParam double radius, @RequestParam double lat,@RequestParam double lon) {
        try {
            Center center = new Center(lon, lat);
            center.setSRID(4326);
            GeometryFactory geometryFactory = new GeometryFactory();
            Coordinate coordinate = new Coordinate(center.lon(), center.lat());
            Point centerPoint = geometryFactory.createPoint(coordinate);
            centerPoint.setSRID(4326);

            return locationService.getLocationWithinArea(centerPoint, radius);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching locations", e);
        }
    }


    @PostMapping("/location")
    public ResponseEntity<String> postLocation(@RequestBody LocationDto locationDto) {
        int id = locationService.addLocation(locationDto);

        return ResponseEntity.created(URI.create("/api/location" + id)).body("New location added");

        //POST: Skapa en ny plats (kräver inloggning).
    }

    @PutMapping("/location/update/{locationId}")
    public ResponseEntity<String> putLocation(@PathVariable("locationId") Integer locationId, @RequestBody LocationDto updedLocationDto) {
        try {
            locationService.updateLocation(updedLocationDto, locationId);
            return ResponseEntity.ok("Location updated successfully" + updedLocationDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    //PUT: Uppdatera en befintlig plats (kräver inloggning).

    @DeleteMapping("location/delete/{locationId}")
    public void deleteLocation(@PathVariable("locationId") Integer locationId) {
        locationService.deleteLocation(locationId);

        // DELETE: Ta bort en befintlig plats (kräver inloggning). Här kan soft
        //  delete vara ett alternativ

    }


}
