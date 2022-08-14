package com.dev.core.security.jwt.securityservice.exception;

import java.util.Map;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public class RoleNotFoundException extends BaseException {

  public RoleNotFoundException(Map<String, Object> data) {
    super(ErrorCode.ROLE_NOT_FOUND, data);
  }
}