package com.dev.core.security.jwt.securityservice.security.jwt;

import static com.dev.core.security.jwt.securityservice.exception.ErrorCode.VERIFY_JWT_FAILED;
import static com.dev.core.security.jwt.securityservice.security.utils.SecurityConstants.BEARER;
import static com.dev.core.security.jwt.securityservice.utils.constants.Constants.EMPTY_VALUE;
import static com.dev.core.security.jwt.securityservice.utils.constants.Constants.ERROR_MESSAGE;

import com.dev.core.security.jwt.securityservice.config.ApplicationProperties;
import com.dev.core.security.jwt.securityservice.exception.InvalidJwtException;
import java.io.IOException;
import java.util.Map;
import java.util.function.Predicate;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  private final ApplicationProperties applicationProperties;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws ServletException, IOException {
    var jwt = resolveToken(request);
    var requestUri = request.getRequestURI();

    if (StringUtils.hasText(jwt) && Boolean.TRUE.equals(jwtTokenProvider.validateToken(jwt))) {
      var authentication = jwtTokenProvider.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("set Authentication to security context for {}, uri: {}",
              authentication.getName(), requestUri);
    } else {
      log.info("no valid JWT token found, uri: {}", requestUri);
      throw new InvalidJwtException(VERIFY_JWT_FAILED,
              Map.of(ERROR_MESSAGE, "no valid JWT token found"));
    }

    chain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return request.getRequestURI().equals("/security-service/v1/authenticate");
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(applicationProperties.getJwtHeader());
    if (validateAuthorizationHeaderValue().test(bearerToken)) {
      return bearerToken.substring(7);
    }
    return EMPTY_VALUE;
  }

  private Predicate<String> validateAuthorizationHeaderValue() {
    return x -> StringUtils.hasText(x) && x.startsWith(BEARER);
  }
}