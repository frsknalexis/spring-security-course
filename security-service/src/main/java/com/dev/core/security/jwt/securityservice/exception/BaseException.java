package com.dev.core.security.jwt.securityservice.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

@Getter
abstract class BaseException extends RuntimeException {
  private final ErrorCode errorCode;

  private final HashMap<String, Object> data = new HashMap<>();

  BaseException(ErrorCode errorCode, Map<String, Object> data) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    if (Optional.ofNullable(data).isPresent()) {
      this.data.putAll(data);
    }
  }

  BaseException(ErrorCode errorCode, Map<String, Object> data, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
    if (Optional.ofNullable(data).isPresent()) {
      this.data.putAll(data);
    }
  }
}