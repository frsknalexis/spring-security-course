package com.dev.core.security.jwt.securityservice.expose.web;

import com.dev.core.security.jwt.securityservice.model.api.response.MessageResponse;
import org.springframework.web.bind.annotation.GetMapping;
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
public class AdminController {

  /**
   * Some javadoc.
   *
   * @return {@link MessageResponse} MessageResponse.
   */
  @GetMapping("/message")
  public MessageResponse getAdminProtectedGreeting() {
    return MessageResponse.builder()
            .message("this is a hidden message")
            .build();
  }
}