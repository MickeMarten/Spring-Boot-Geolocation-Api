package org.example.geolocation.geolocations.repository;

import org.example.geolocation.geolocations.dto.LocationDto;
import org.example.geolocation.geolocations.entity.Location;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends ListCrudRepository<Location, Integer> {

    Optional<Location> findByIdAndIsPublic(Integer id, Boolean isPublic);

    List<Location> findByCategoryIdAndIsPublic(Integer categoryId, Boolean isPublic);

    @Query("SELECT l FROM Location l WHERE ST_Distance(l.coordinate, :center) <= :radius")
    List<Location> findAllWithinRadius(Point center, double radius);

    Optional<Location> findByName(String name);

}

//https://spring.io/blog/2022/02/22/announcing-listcrudrepository-friends-for-spring-data-3-0
//When creating a query that add, change or remove entities from the database we need to add the
//@Modifying annotation.
//@Query("UPDATE Organization SET name = :prefix || firstName")
//@Modifying
//void addPrefixToName(@Param("prefix") String prefix);
//Will throw exception if we try to run it if not in a Transaction.
//Add @Transactional to method or calling method. (Unit of Work)