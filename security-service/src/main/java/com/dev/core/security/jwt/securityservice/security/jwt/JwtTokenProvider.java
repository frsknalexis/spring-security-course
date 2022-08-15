package com.dev.core.security.jwt.securityservice.security.jwt;

import static com.dev.core.security.jwt.securityservice.exception.ErrorCode.VERIFY_JWT_FAILED;
import static com.dev.core.security.jwt.securityservice.security.utils.SecurityConstants.AUTHORITIES_KEY;
import static com.dev.core.security.jwt.securityservice.security.utils.SecurityConstants.USER_KEY;
import static com.dev.core.security.jwt.securityservice.utils.constants.Constants.COMMA_DELIMITER;
import static com.dev.core.security.jwt.securityservice.utils.constants.Constants.DETAIL;
import static com.dev.core.security.jwt.securityservice.utils.constants.Constants.EMPTY_VALUE;
import static com.dev.core.security.jwt.securityservice.utils.constants.Constants.ERROR_MESSAGE;
import static java.lang.Boolean.TRUE;

import com.dev.core.security.jwt.securityservice.config.ApplicationProperties;
import com.dev.core.security.jwt.securityservice.exception.InvalidJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {

  private final String base64Secret;

  private final Long tokenValidityInMilliSeconds;

  private final Long tokenValidityInMilliSecondsForRememberMe;

  private Key key;

  /**
   * Method constructor.
   */
  public JwtTokenProvider(ApplicationProperties applicationProperties) {
    this.base64Secret = applicationProperties.getBase64Secret();
    this.tokenValidityInMilliSeconds = applicationProperties.getTokenValidityInSeconds() * 1000;
    this.tokenValidityInMilliSecondsForRememberMe =
       applicationProperties.getTokenValidityInSecondsForRememberMe() * 1000;
  }

  @Override
  public void afterPropertiesSet() {
    var keyBytes = Decoders.BASE64.decode(base64Secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Method that create the token for authentication and authorization.
   *
   * @param authentication authentication.
   * @param rememberMe rememberMe.
   * @return {@link String} the token created.
   */
  public String createToken(Authentication authentication, Boolean rememberMe) {
    String authorities = Optional.ofNullable(authentication)
            .map(Authentication::getAuthorities)
            .map(grantedAuthorities -> grantedAuthorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(COMMA_DELIMITER)))
            .orElse(EMPTY_VALUE);

    var now = new Date().getTime();
    var validity = Boolean.TRUE.equals(rememberMe)
            ? new Date(now + this.tokenValidityInMilliSecondsForRememberMe)
            : new Date(now + this.tokenValidityInMilliSeconds);

    return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(USER_KEY, authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
  }

  /**
   * Method that get the Authentication Object.
   *
   * @param token token.
   * @return {@link Authentication} Authentication Object.
   */
  public Authentication getAuthentication(String token) {
    var claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

    var username = claims.get(USER_KEY, String.class);
    var authorities = claims.get(AUTHORITIES_KEY, String.class);

    var authoritiesList = Arrays.stream(authorities.split(COMMA_DELIMITER))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(username, token, authoritiesList);
  }

  /**
   * Method that validate the token.
   *
   * @param token token.
   * @return {@link Boolean} Boolean value.
   */
  public Boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token);
      return TRUE;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature.");
      log.info("Invalid JWT signature trace: {}", e.toString());
      throw new InvalidJwtException(VERIFY_JWT_FAILED,
              Map.of(ERROR_MESSAGE, "Invalid JWT signature.", DETAIL, e.getMessage()));
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token.");
      log.info("Expired JWT token trace: {}", e.toString());
      throw new InvalidJwtException(VERIFY_JWT_FAILED,
              Map.of(ERROR_MESSAGE, "Expired JWT token.", DETAIL, e.getMessage()));
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
      log.info("Unsupported JWT token trace: {}", e.toString());
      throw new InvalidJwtException(VERIFY_JWT_FAILED,
              Map.of(ERROR_MESSAGE, "Unsupported JWT token.", DETAIL, e.getMessage()));
    } catch (IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
      log.info("JWT token compact of handler are invalid trace: {}", e.toString());
      throw new InvalidJwtException(VERIFY_JWT_FAILED,
              Map.of(ERROR_MESSAGE, "JWT token compact of handler are invalid.",
                      DETAIL, e.getMessage()));
    }
  }
}