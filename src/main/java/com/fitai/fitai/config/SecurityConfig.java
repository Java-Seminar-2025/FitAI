package com.fitai.fitai.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
              .csrf(csrf -> csrf.disable())                              // REST API, testiranje kroz Postman
              .headers(h -> h.frameOptions(f -> f.sameOrigin()))         // da radi H2 konzola
              .authorizeHttpRequests(auth -> auth
                      .requestMatchers(PathRequest.toH2Console()).permitAll() // samo za razvoj!
                      .requestMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()
                      .requestMatchers("/api/users/me").authenticated()
                      // svaki prijavljeni korisnik smije pozvati PUT/DELETE, a servis provjerava je li vlasnik
                      .requestMatchers(HttpMethod.PUT, "/api/users/*").authenticated()
                      .requestMatchers(HttpMethod.DELETE, "/api/users/*").authenticated()
                      // popis svih korisnika i dohvat tuđeg računa samo za ADMIN
                      .requestMatchers("/api/users", "/api/users/**").hasRole("ADMIN")
                      .anyRequest().authenticated())
              .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
              .logout(l -> l
                      .logoutUrl("/api/auth/logout")
                      .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK)));
      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
   }
}