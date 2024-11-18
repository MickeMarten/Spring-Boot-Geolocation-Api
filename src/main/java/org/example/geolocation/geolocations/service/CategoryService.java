package org.example.geolocation.geolocations.service;

import org.example.geolocation.geolocations.dto.CategoryDto;
import org.example.geolocation.geolocations.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

//@Transactional Körs i sin helhet eller inte alls.
// HTttp cookies only
    //Base 64 security finns inbyggt i java. Base64
        public String allCategorys(){
        return "All Categories";
        }

}
