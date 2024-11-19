package org.example.geolocation.geolocations.controller;

import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.entity.Location;
import org.example.geolocation.geolocations.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    }

    @GetMapping("/locations/public/{locationId}")
    public LocationDto publicLocationId(@PathVariable("locationId") Integer locationId) {
        return locationService.getPublicLocationById(locationId);
        //en specifik publik plats (för anonyma användare).
    }

    @GetMapping("/location/public/category/{categoryId}")
    public Integer publicLocationCategoryId(@PathVariable("categoryId") Integer categoryId) {
        return categoryId;
      //Hämta alla publika platser inom en specifik kategori.
    }

    @GetMapping("location/user")
    public String userLocation() {
        return "user connected with location";
        //Hämta alla platser (både publika och privata) som tillhör den
        //inloggade användaren.
    }

    @GetMapping("location/public/area")
    public String publicLocationArea() {
        return "public";

        //Hämta alla platser inom en viss yta (radie från ett centrum eller
        //hörn på en kvadrat).
    }

    @PostMapping("/location")
    public Location postLocation(@RequestBody Location location) {
        return location;
        //POST: Skapa en ny plats (kräver inloggning).
    }

    @PutMapping("/location/{locationId}")
    public Location putLocation(@PathVariable("locationId") Integer locationId, @RequestBody Location location) {
        return location;
        //PUT: Uppdatera en befintlig plats (kräver inloggning).
    }

    @DeleteMapping("location/{locationId}")
    public void deleteLocation(@PathVariable("locationId") Integer locationId) {
        // DELETE: Ta bort en befintlig plats (kräver inloggning). Här kan soft
      //  delete vara ett alternativ

    }


}
