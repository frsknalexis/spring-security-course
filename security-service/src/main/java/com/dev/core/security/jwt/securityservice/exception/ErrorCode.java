package com.dev.core.security.jwt.securityservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Getter
public enum ErrorCode {

  USERNAME_ALREADY_EXIST(1001, HttpStatus.BAD_REQUEST, "Username already exists"),
  ROLE_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "No se encontro el rol especificado"),
  USERNAME_NOT_FOUND(1003, HttpStatus.NOT_FOUND, "No se encontro el usuario especificado"),
  VERIFY_JWT_FAILED(1004, HttpStatus.UNAUTHORIZED, "La verificacion del token fallo"),
  METHOD_ARGUMENT_NOT_VALID(1005, HttpStatus.BAD_REQUEST, "La validacion del parametro fallo");
  private final Integer code;

  private final HttpStatus httpStatus;

  private final String message;

  ErrorCode(Integer code, HttpStatus httpStatus, String message) {
    this.code = code;
    this.httpStatus = httpStatus;
    this.message = message;
  }
}