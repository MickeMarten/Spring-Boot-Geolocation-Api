package org.example.geolocation.geolocations.dto;

import org.example.geolocation.geolocations.entity.Category;

public record CategoryDto(Integer id, String name, String symbol, String description) {

public static CategoryDto convertToDto(Category category) {
    return new CategoryDto(category.getId(), category.getName(), category.getSymbol(), category.getDescription());
}

}
