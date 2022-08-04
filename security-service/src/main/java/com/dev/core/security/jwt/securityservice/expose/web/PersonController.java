package com.dev.core.security.jwt.securityservice.expose.web;

import com.dev.core.security.jwt.securityservice.model.api.response.PersonResponse;
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
public class PersonController {

  /**
   * Some javadoc.
   *
   * @return {@link PersonResponse} PersonResponse.
   */
  @GetMapping("/person")
  public PersonResponse getPerson() {
    return PersonResponse.builder()
            .name("John Doe")
            .email("john.doe@test.org")
            .build();
  }
}