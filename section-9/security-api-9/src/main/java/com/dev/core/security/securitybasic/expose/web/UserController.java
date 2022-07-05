package com.dev.core.security.securitybasic.expose.web;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@RestController
public class UserController {

  @GetMapping("/user")
  public String getUserDetails(Principal principal) {
    return principal.getName();
  }
}