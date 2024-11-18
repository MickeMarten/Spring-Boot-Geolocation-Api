package org.example.geolocation.geolocations.controller;

import org.example.geolocation.geolocations.dto.CategoryDto;
import org.example.geolocation.geolocations.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class CategoryController {

    CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    @PreAuthorize("permitAll()")

    public String category() {

        return "All categories";

        // GET: Hämta alla kategorier



    }

    @GetMapping("/categories/{categoryId}")
    @PreAuthorize("permitAll()")
    public Integer categoryById(@PathVariable Integer categoryId) {
        return categoryId;
        // Hämta en specifik kategori
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public String category(@RequestBody CategoryDto categoryDto) {
        return "create category";

        //POST: Skapa en ny kategori (kräver adminroll). Namnet får inte
        //kollidera med en befintlig kategori.
    }
}
