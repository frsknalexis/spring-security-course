package com.dev.core.security.jwt.securityservice.exception;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

  /**
   * Method that handle BaseException.
   *
   * @param ex BaseException.
   * @param request HttpServletRequest.
   * @return ResponseEntity&lt;ErrorResponse&gt; ErrorResponse.
   */
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex,
      HttpServletRequest request) {
    var errorResponse = new ErrorResponse(ex, request.getRequestURI());
    log.info("occurred BaseException: " + errorResponse);
    return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(errorResponse);
  }

  /**
   * Method that handle MethodArgumentNotValidException.
   *
   * @param ex MethodArgumentNotValidException.
   * @param request HttpServletRequest.
   * @return ResponseEntity&lt;ErrorResponse&gt; ErrorResponse.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    Map<String, Object> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors()
            .forEach(error -> {
              var fieldName = ((FieldError) error).getField();
              var errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    var errorResponse = new ErrorResponse(ErrorCode.METHOD_ARGUMENT_NOT_VALID,
            request.getRequestURI(), errors);
    log.info("occurred MethodArgumentNotValidException: " + errorResponse);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Method that handle UsernameAlreadyExistException.
   *
   * @param ex UsernameAlreadyExistException.
   * @param request HttpServletRequest.
   * @return ResponseEntity&lt;ErrorResponse&gt; ErrorResponse.
   */
  @ExceptionHandler(UsernameAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistException(
      UsernameAlreadyExistException ex, HttpServletRequest request) {
    var errorResponse = new ErrorResponse(ex, request.getRequestURI());
    log.info("occurred UsernameAlreadyExistException: " + errorResponse);
    return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(errorResponse);
  }

  /**
   * Method that handle UserNotFoundException.
   *
   * @param ex BaseException.
   * @param request HttpServletRequest.
   * @return ResponseEntity&lt;ErrorResponse&gt; ErrorResponse.
   */
  @ExceptionHandler(value = { RoleNotFoundException.class, UsernameNotFoundException.class })
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(BaseException ex,
      HttpServletRequest request) {
    var errorResponse = new ErrorResponse(ex, request.getRequestURI());
    log.info("occurred UserNotFoundException: " + errorResponse);
    return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(errorResponse);
  }
}