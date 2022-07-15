package com.dev.core.security.securitybasic.utils.constants;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public class Constants {

  private Constants() {

  }

  public static final String JWT_KEY;

  public static final String HEADER_AUTHORIZATION;

  public static final String USERNAME_CLAIM;
  public static final String AUTHORITIES_CLAIM;

  public static final String EMPTY_VALUE;

  public static final String COMMA_DELIMITER;

  static {
    JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    HEADER_AUTHORIZATION = "Authorization";
    USERNAME_CLAIM = "username";
    AUTHORITIES_CLAIM = "authorities";
    EMPTY_VALUE = "";
    COMMA_DELIMITER = ",";
  }
}