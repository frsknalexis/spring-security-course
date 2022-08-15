package com.dev.core.security.jwt.securityservice.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final HandlerExceptionResolver handlerExceptionResolver;

  public JwtAuthenticationEntryPoint(@Autowired @Qualifier("handlerExceptionResolver")
                                     HandlerExceptionResolver handlerExceptionResolver) {
    this.handlerExceptionResolver = handlerExceptionResolver;
  }

  /**
   * This is invoked when user tries to access a secured REST resource
   * without supplying any credentials, we should just send a 401 Unauthorized response
   * because there is no 'login page' to redirect to, here you can place any message you want.
   *
   * @param request that resulted in an <code>AuthenticationException</code>
   * @param response so that the user agent can begin authentication
   * @param authException that caused the invocation
   * @throws IOException IOException.
   */
  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    log.error("User is unauthorised. Routing from the entry point");

    if (request.getAttribute("javax.servlet.error.exception") != null) {
      Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
      handlerExceptionResolver.resolveException(request, response, null, (Exception) throwable);
    }
    if (!response.isCommitted()) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
  }
}