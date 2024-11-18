package org.example.geolocation.geolocations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geolatte.geom.json.GeolatteGeomModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class ApplicationConfig {

    @Bean
    public GeolatteGeomModule geolatteGeomModule() {
        return new GeolatteGeomModule();
    }

    @Bean
    public ObjectMapper objectMapper(GeolatteGeomModule geolatteGeomModule) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(geolatteGeomModule);
        return objectMapper;
    }
}
