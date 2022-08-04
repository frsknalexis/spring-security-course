package com.dev.core.security.jwt.securityservice.security.jwt;

import static com.dev.core.security.jwt.securityservice.security.utils.SecurityConstants.BEARER;
import static com.dev.core.security.jwt.securityservice.utils.constants.Constants.EMPTY_VALUE;

import com.dev.core.security.jwt.securityservice.config.ApplicationProperties;
import java.io.IOException;
import java.util.function.Predicate;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  private final ApplicationProperties applicationProperties;

  @Override
  public void doFilter(ServletRequest request,
      ServletResponse response, FilterChain chain) throws IOException, ServletException {
    var httpServletRequest = (HttpServletRequest) request;
    var jwt = resolveToken(httpServletRequest);
    var requestUri = httpServletRequest.getRequestURI();

    if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
      var authentication = jwtTokenProvider.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("set Authentication to security context for {}, uri: {}",
              authentication.getName(), requestUri);
    } else {
      log.info("no valid JWT token found, uri: {}", requestUri);
    }

    chain.doFilter(request, response);
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