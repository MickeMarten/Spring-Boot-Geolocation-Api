package org.example.geolocation.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.core.env.Environment;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Security {

    private final Environment env;

    public Security(Environment env) {
        this.env = env;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.POST, "/api/categories").hasAuthority("admin")
                                .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/locations/public/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/locations/user/").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/location").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/location/**").hasAuthority("admin")
                                .requestMatchers(HttpMethod.PUT, "/api/location/**").authenticated()
                                .requestMatchers("/error").permitAll()
                                .anyRequest().denyAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        if (!isTestProfileActive()) {
            http.oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(jwt -> jwt
                            .jwtAuthenticationConverter(jwtAthenticationConverter())));
        }

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    private boolean isTestProfileActive() {
        return Arrays.asList(env.getActiveProfiles()).contains("test");
    }
}