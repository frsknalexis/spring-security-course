package com.dev.core.security.securitybasic.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.dev.core.security.securitybasic.security.filter.AuthoritiesLoggingAfterFilter;
import com.dev.core.security.securitybasic.security.filter.AuthoritiesLoggingAtFilter;
import com.dev.core.security.securitybasic.security.filter.JwtTokenGeneratorFilter;
import com.dev.core.security.securitybasic.security.filter.JwtTokenValidationFilter;
import com.dev.core.security.securitybasic.security.filter.RequestValidationBeforeFilter;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Configuration
@EnableWebSecurity
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
              configuration.setExposedHeaders(Collections.singletonList("Authorization"));
              configuration.setMaxAge(3600L);
              return configuration;
            })
            .and()
            .csrf()
            .disable()
            .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
            .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/account").hasRole("USER")
            .antMatchers("/balance").hasAnyRole("USER", "ADMIN")
            .antMatchers("/loans").hasRole("ROOT")
            .antMatchers("/cards").authenticated()
            .antMatchers("/user").authenticated()
            .antMatchers("/notices", "/contact").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}