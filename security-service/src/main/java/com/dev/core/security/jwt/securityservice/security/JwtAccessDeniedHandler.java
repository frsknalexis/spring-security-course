package com.dev.core.security.jwt.securityservice.security;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  /**
   * This is invoked when user tries to access a secured REST resource
   * without the necessary authorizationWe should just send a 403 Forbidden response
   * because there is no 'error' page to redirect to, here you can place any message you want.
   *
   * @param request that resulted in an <code>AccessDeniedException</code>
   * @param response so that the user agent can be advised of the failure
   * @param accessDeniedException that caused the invocation
   * @throws IOException IOException.
   */
  @Override
  public void handle(HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    response.sendError(SC_FORBIDDEN, accessDeniedException.getMessage());
  }
}