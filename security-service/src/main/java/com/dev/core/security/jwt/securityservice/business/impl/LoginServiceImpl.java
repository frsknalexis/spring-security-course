package com.dev.core.security.jwt.securityservice.business.impl;

import static com.dev.core.security.jwt.securityservice.security.utils.SecurityConstants.BEARER;
import static java.lang.Boolean.FALSE;

import com.dev.core.security.jwt.securityservice.business.LoginService;
import com.dev.core.security.jwt.securityservice.config.ApplicationProperties;
import com.dev.core.security.jwt.securityservice.model.api.request.LoginRequest;
import com.dev.core.security.jwt.securityservice.model.api.response.LoginResponse;
import com.dev.core.security.jwt.securityservice.security.jwt.JwtTokenProvider;
import io.vavr.Tuple2;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final JwtTokenProvider jwtTokenProvider;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final ApplicationProperties applicationProperties;

  @Override
  public Tuple2<LoginResponse, HttpHeaders> authenticate(LoginRequest request) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(),
            request.getPassword());

    var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    var rememberMe = (Optional.ofNullable(request.getRememberMe()).isEmpty())
            ? FALSE : request.getRememberMe();

    var token = jwtTokenProvider.createToken(authentication, rememberMe);

    var httpHeaders = new HttpHeaders();
    httpHeaders.add(applicationProperties.getJwtHeader(), BEARER.concat(token));

    var loginResponse = LoginResponse.builder()
            .token(token)
            .build();

    return new Tuple2<>(loginResponse, httpHeaders);
  }
}