package org.example.geolocation.geolocations.repository;

import org.example.geolocation.geolocations.entity.Location;
import org.springframework.data.repository.ListCrudRepository;

public interface LocationRepository extends ListCrudRepository<Location, Integer> {



}
