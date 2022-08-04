package com.dev.core.security.jwt.securityservice.security.utils;

import static org.apache.logging.log4j.util.Strings.EMPTY;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Slf4j
public class SecurityUtils {

  private SecurityUtils() {  }

  /**
   * Method that get the currentUsername.
   *
   * @return {@link String} currentUsername.
   */
  public static String getCurrentUsername() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .map(authentication -> {
              String username = authentication.getName();
              log.info("found username {} in security context", username);
              return username;
            })
            .orElseGet(() -> {
              log.info("no authentication in security context found");
              return EMPTY;
            });
  }
}