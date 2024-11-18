package org.example.geolocation.geolocations.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class LocationController {

    @GetMapping("/location/public")

    public String publicLocation() {
        return "all public locations";
        //Hämta alla publika platser
    }

    @GetMapping("/location/public/{locationId}")
    public Integer publicLocationId(@PathVariable("locationId") Integer locationId) {
        return locationId;
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
