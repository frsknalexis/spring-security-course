package com.dev.core.security.securitybasic.security.filter;

import static com.dev.core.security.securitybasic.utils.constants.Constants.AUTHORITIES_CLAIM;
import static com.dev.core.security.securitybasic.utils.constants.Constants.COMMA_DELIMITER;
import static com.dev.core.security.securitybasic.utils.constants.Constants.EMPTY_VALUE;
import static com.dev.core.security.securitybasic.utils.constants.Constants.HEADER_AUTHORIZATION;
import static com.dev.core.security.securitybasic.utils.constants.Constants.JWT_KEY;
import static com.dev.core.security.securitybasic.utils.constants.Constants.USERNAME_CLAIM;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .ifPresent(authentication -> {
              SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
              String jwt = Jwts.builder()
                      .setIssuer("Eazy Bank")
                      .setSubject(authentication.getName())
                      .claim(USERNAME_CLAIM, authentication.getName())
                      .claim(AUTHORITIES_CLAIM, populateAuthorities(authentication))
                      .setIssuedAt(new Date())
                      .setExpiration(new Date(new Date().getTime() + 300000000))
                      .signWith(key)
                      .compact();
              response.setHeader(HEADER_AUTHORIZATION, jwt);
            });
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return !request.getServletPath().equals("/user");
  }

  private static String populateAuthorities(Authentication authentication) {
    return Optional.ofNullable(authentication)
            .map(Authentication::getAuthorities)
            .map(grantedAuthorities -> grantedAuthorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(COMMA_DELIMITER)))
            .orElse(EMPTY_VALUE);
  }
}