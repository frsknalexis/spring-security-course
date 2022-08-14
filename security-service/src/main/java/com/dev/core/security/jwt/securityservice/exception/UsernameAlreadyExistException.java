package com.dev.core.security.jwt.securityservice.exception;

import static com.dev.core.security.jwt.securityservice.exception.ErrorCode.USERNAME_ALREADY_EXIST;

import java.util.Map;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public class UsernameAlreadyExistException extends BaseException {

  public UsernameAlreadyExistException(Map<String, Object> data) {
    super(USERNAME_ALREADY_EXIST, data);
  }
}