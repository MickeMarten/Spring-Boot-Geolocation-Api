package org.example.geolocation.geolocations.service;

import org.example.geolocation.geolocations.dto.CategoryDto;
import org.example.geolocation.geolocations.entity.Category;
import org.example.geolocation.geolocations.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        public Optional<CategoryDto> getCategoryById(int id) {
            return categoryRepository.findById(id).map(CategoryDto::convertToDto);
        }

        public int addCategory(CategoryDto categoryDto) {
        if(categoryRepository.existsByName(categoryDto.name())){
            throw new IllegalArgumentException("Category name already exists" + categoryDto.name());
        }
        Category category = new Category();
        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());
        category.setSymbol(categoryDto.symbol());
        categoryRepository.save(category);
        return category.getId();
        }



}
