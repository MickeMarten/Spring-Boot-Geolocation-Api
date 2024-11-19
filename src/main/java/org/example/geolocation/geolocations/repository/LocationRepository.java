package org.example.geolocation.geolocations.repository;

import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.entity.Location;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface LocationRepository extends ListCrudRepository<Location, Integer> {


}
