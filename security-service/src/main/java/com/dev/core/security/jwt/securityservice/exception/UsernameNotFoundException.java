package com.dev.core.security.jwt.securityservice.exception;

import static com.dev.core.security.jwt.securityservice.exception.ErrorCode.USERNAME_NOT_FOUND;

import java.util.Map;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public class UsernameNotFoundException extends BaseException {

  public UsernameNotFoundException(Map<String, Object> data) {
    super(USERNAME_NOT_FOUND, data);
  }
}