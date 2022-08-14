package com.dev.core.security.jwt.securityservice.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Getter
@NoArgsConstructor
@ToString
public class ErrorResponse {

  private Integer code;

  private Integer status;

  private String message;

  private String path;

  private LocalDateTime timestamp;

  private final HashMap<String, Object> errorDetails = new HashMap<>();

  public ErrorResponse(BaseException ex, String path) {
    this(ex.getErrorCode().getCode(), ex.getErrorCode().getHttpStatus().value(),
            ex.getErrorCode().getMessage(), path, ex.getData());
  }

  public ErrorResponse(ErrorCode errorCode, String path) {
    this(errorCode.getCode(), errorCode.getHttpStatus().value(), errorCode.getMessage(),
            path, null);
  }

  public ErrorResponse(ErrorCode errorCode, String path, Map<String, Object> errorDetails) {
    this(errorCode.getCode(), errorCode.getHttpStatus().value(), errorCode.getMessage(),
            path, errorDetails);
  }

  private ErrorResponse(Integer code, Integer status, String message, String path,
      Map<String, Object> errorDetails) {
    this.code = code;
    this.status = status;
    this.message = message;
    this.path = path;
    this.timestamp = LocalDateTime.now();
    if (Optional.ofNullable(errorDetails).isPresent()) {
      this.errorDetails.putAll(errorDetails);
    }
  }
}