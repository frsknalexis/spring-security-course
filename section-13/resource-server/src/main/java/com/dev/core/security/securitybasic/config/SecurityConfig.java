package com.dev.core.security.securitybasic.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Collections;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Custom configuration spring security.
   * /account - secured
   * /balance - secured
   * /loans - secured
   * /cards - secured
   * /notices - not secured
   * /contact - not secured
   *
   * @param http http.
   * @throws Exception Exception.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());

    http.sessionManagement()
            .sessionCreationPolicy(STATELESS)
            .and()
            .cors()
            .configurationSource(request -> {
              CorsConfiguration configuration = new CorsConfiguration();
              configuration.setAllowedOrigins(Collections.singletonList("*"));
              configuration.setAllowedMethods(Collections.singletonList("*"));
              configuration.setAllowCredentials(Boolean.TRUE);
              configuration.setAllowedHeaders(Collections.singletonList("*"));
              configuration.setMaxAge(3600L);
              return configuration;
            })
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/account").hasAnyRole("USER")
            .antMatchers("/balance").hasAnyRole("ADMIN")
            .antMatchers("/loans").authenticated()
            .antMatchers("/cards").hasAnyRole("USER", "ADMIN")
            .antMatchers("/user").authenticated()
            .antMatchers("/notices", "/contact").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthenticationConverter);
  }
}