package org.example.geolocation.geolocations.service;

import org.example.geolocation.geolocations.dto.CategoryDto;
import org.example.geolocation.geolocations.entity.Category;
import org.example.geolocation.geolocations.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

        public List<CategoryDto>allCategories(){

            return categoryRepository.findAll().stream()
                    .map(CategoryDto::convertToDto).toList();
        }

}
