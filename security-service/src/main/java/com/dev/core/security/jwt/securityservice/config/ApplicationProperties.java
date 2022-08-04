package com.dev.core.security.jwt.securityservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Configuration
@Getter
@Setter
public class ApplicationProperties {

  @Value("${jwt.header}")
  private String jwtHeader;

  @Value("${jwt.base64-secret}")
  private String base64Secret;

  @Value("${jwt.token-validity-in-seconds}")
  private Long tokenValidityInSeconds;

  @Value("${jwt.token-validity-in-seconds-for-remember-me}")
  private Long tokenValidityInSecondsForRememberMe;
}