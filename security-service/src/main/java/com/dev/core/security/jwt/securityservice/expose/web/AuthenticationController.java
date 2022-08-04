package com.dev.core.security.jwt.securityservice.expose.web;

import com.dev.core.security.jwt.securityservice.business.LoginService;
import com.dev.core.security.jwt.securityservice.model.api.request.LoginRequest;
import com.dev.core.security.jwt.securityservice.model.api.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

  private final LoginService loginService;

  /**
   * Method that perform login action.
   *
   * @param request request.
   * @return {@link LoginRequest} LoginRequest
   */
  @PostMapping("/authenticate")
  public ResponseEntity<LoginResponse> authenticate(@Validated @RequestBody LoginRequest request) {
    return ResponseEntity.ok()
            .headers(loginService.authenticate(request)._2)
            .body(loginService.authenticate(request)._1);
  }
}