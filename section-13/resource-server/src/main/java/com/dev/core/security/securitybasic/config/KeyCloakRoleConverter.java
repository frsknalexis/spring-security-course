package com.dev.core.security.securitybasic.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public class KeyCloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

    if (realmAccess == null || realmAccess.isEmpty()) {
      return new ArrayList<>();
    }

    return ((List<String>) realmAccess.get("roles"))
            .stream()
            .map(role -> "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
  }
}