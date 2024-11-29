package org.example.geolocation.geolocations.controller;

import jakarta.validation.Valid;
import org.example.geolocation.geolocations.dto.CategoryDto;
import org.example.geolocation.geolocations.entity.Category;
import org.example.geolocation.geolocations.repository.CategoryRepository;
import org.example.geolocation.geolocations.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class CategoryController {

    CategoryRepository categoryRepository;
    CategoryService categoryService;
    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    @PreAuthorize("permitAll()")

    public ResponseEntity<List<CategoryDto>> categories() {
        List<CategoryDto> categories = categoryService.allCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(categories);
        }

    }

    @GetMapping("/categories/{categoryId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CategoryDto> categoryById(@PathVariable Integer categoryId) {

        return categoryService.getCategoryById(categoryId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/categories")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto) {
        try {
            int id = categoryService.addCategory(categoryDto);
            return ResponseEntity.created(URI.create("/api/categories/" + id)).body("Category created");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
