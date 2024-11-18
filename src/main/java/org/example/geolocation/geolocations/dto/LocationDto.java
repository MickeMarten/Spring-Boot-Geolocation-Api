package org.example.geolocation.geolocations.dto;

import java.time.LocalDateTime;

public record LocationDto(Integer id, String name, Integer category_id, String description, Integer user_id,
                          LocalDateTime created, LocalDateTime modified_at, Boolean availability) {
}
