package com.dev.core.security.jwt.securityservice.security.jwt;

import com.dev.core.security.jwt.securityservice.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter
        <DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtTokenProvider jwtTokenProvider;

  private final ApplicationProperties applicationProperties;

  @Override
  public void configure(HttpSecurity httpSecurity) {
    JwtFilter jwtFilter = new JwtFilter(jwtTokenProvider, applicationProperties);
    httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
}