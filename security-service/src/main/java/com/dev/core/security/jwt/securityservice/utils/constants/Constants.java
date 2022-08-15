package com.dev.core.security.jwt.securityservice.utils.constants;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public class Constants {

  private Constants() {  }

  public static final String EMPTY_VALUE;

  public static final String COMMA_DELIMITER;

  public static final String USERNAME;

  public static final String ERROR_MESSAGE;

  public static final String DETAIL;

  static {
    EMPTY_VALUE = "";
    COMMA_DELIMITER = ",";
    USERNAME = "username";
    ERROR_MESSAGE = "errorMessage";
    DETAIL = "detail";
  }
}