package org.example.geolocation.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
public class Security {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("adminpass")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("userpass")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    //Konfigurera security här.
    //Deny by default och sedan öppnar upp.
    //Anomyenus
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                        .requestMatchers(HttpMethod.POST,"/api/categories").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/location").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/locations/public/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/locations/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/api/location/**").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/api/location/**").permitAll()
                                .requestMatchers("/error").permitAll()
                        .anyRequest().denyAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }


}

