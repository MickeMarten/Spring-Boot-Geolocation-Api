package org.example.geolocation.geolocations.dto;

import org.example.geolocation.geolocations.entity.Location;
import org.geolatte.geom.Point;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record LocationDto(Integer id, String name, Integer category_id, String description,
                          LocalDateTime created, LocalDateTime modified_at, Boolean is_Public,
                          Point coordinate, Boolean deleted) {

    public static LocationDto convertToDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getName(),
                location.getCategory().getId(),
                location.getDescription(),
                LocalDateTime.ofInstant(location.getCreated(), ZoneOffset.UTC),
                LocalDateTime.ofInstant(location.getModifiedAt(), ZoneOffset.UTC),
                location.getIsPublic(),
                location.getCoordinate(),
                location.getDeleted()
        );
    }


}