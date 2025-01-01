package org.example.geolocation.geolocations.controller;
import org.example.geolocation.geolocations.centerpoint.CenterPoint;
import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.service.LocationService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.security.Principal;
import java.util.List;


@RequestMapping("/api")
@RestController
public class LocationController {

    LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations/public")
    @PreAuthorize("permitAll()")
    public List<LocationDto> publicLocations(LocationDto locationDto) {
        return locationService.getAllPublicLocations(locationDto);



    }

    @GetMapping("/locations/public/{locationId}")
    @PreAuthorize("permitAll()")
    public LocationDto publicLocationId(@PathVariable("locationId") Integer locationId) {
        return locationService.getPublicLocationById(locationId);

    }

    @GetMapping("/locations/public/category/{categoryId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<LocationDto>> publicLocationCategoryId(@PathVariable("categoryId") Integer categoryId) {
        List<LocationDto> locations = locationService.getPublicLocationsByCategory(categoryId);
        if (locations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(locations);

    }

    @GetMapping("/locations/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LocationDto>> getUserLocations(Principal principal) {
        List<LocationDto> locations = locationService.getLocationsForUser(principal);

        if (locations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(locations);
        }

        return ResponseEntity.ok(locations);
    }



    @GetMapping("locations/public/area")
    @PreAuthorize("permitAll()")
    public List<LocationDto> getLocationsWithinRadius(@RequestParam double radius, @RequestParam double lat,@RequestParam double lon) {
        try {
            CenterPoint center = new CenterPoint(lon, lat);
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> postLocation(@RequestBody LocationDto locationDto, Principal principal) {
        try {
            int id = locationService.addLocation(locationDto, principal);
            return ResponseEntity.created(URI.create("/api/location/" + id)).body("New location added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/location/update/{locationId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> putLocation(@PathVariable("locationId") Integer locationId, @RequestBody LocationDto updedLocationDto) {
        try {
            locationService.updateLocation(updedLocationDto, locationId);
            return ResponseEntity.ok("Location updated successfully" + updedLocationDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }




    @DeleteMapping("location/delete/{locationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteLocation(@PathVariable("locationId") Integer locationId) {
        locationService.deleteLocation(locationId);


    }


}
