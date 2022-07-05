package com.dev.core.security.securitybasic.security.provider;

import com.dev.core.security.securitybasic.model.entity.Authority;
import com.dev.core.security.securitybasic.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Component
@RequiredArgsConstructor
public class AppUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

  private final CustomerRepository customerRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    var username = authentication.getName();
    var password = authentication.getCredentials().toString();
    return Optional.ofNullable(customerRepository.findByEmail(username))
            .map(customer -> {
              if (passwordEncoder.matches(password, customer.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username,
                        password, getAuthorities(customer.getAuthorities()));
              }
              throw new BadCredentialsException("Invalid Password");
            })
            .orElseThrow(() -> new BadCredentialsException("No user registered with this details"));
  }

  private static List<GrantedAuthority> getAuthorities(Set<Authority> authorities) {
    return authorities.stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}