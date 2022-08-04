package com.dev.core.security.jwt.securityservice.business;

import com.dev.core.security.jwt.securityservice.model.api.request.LoginRequest;
import com.dev.core.security.jwt.securityservice.model.api.response.LoginResponse;
import io.vavr.Tuple2;
import org.springframework.http.HttpHeaders;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public interface LoginService {

  Tuple2<LoginResponse, HttpHeaders> authenticate(LoginRequest request);
}