package com.dev.core.security.jwt.securityservice.security.utils;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public class SecurityConstants {

  private SecurityConstants() {  }

  public static final String AUTHORITIES_KEY;

  public static final String USER_KEY;

  public static final String BEARER;

  static {
    AUTHORITIES_KEY = "authorities";
    USER_KEY = "user";
    BEARER = "Bearer ";
  }
}