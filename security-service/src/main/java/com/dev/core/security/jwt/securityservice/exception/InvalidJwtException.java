package com.dev.core.security.jwt.securityservice.exception;

import java.util.Map;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public class InvalidJwtException extends BaseException {

  public InvalidJwtException(ErrorCode errorCode, Map<String, Object> data) {
    super(errorCode, data);
  }
}