package org.example.geolocation.geolocations.repository;

import org.example.geolocation.geolocations.entity.Category;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryRepository extends ListCrudRepository<Category,Integer> {
}
