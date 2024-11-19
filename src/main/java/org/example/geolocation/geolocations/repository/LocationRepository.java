package org.example.geolocation.geolocations.repository;

import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.entity.Location;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends ListCrudRepository<Location, Integer> {

    Optional<Location> findByIdAndIsPublic(Integer id, Boolean isPublic);

}
