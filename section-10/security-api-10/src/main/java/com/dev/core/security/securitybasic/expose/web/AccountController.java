package com.dev.core.security.securitybasic.expose.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@RestController
public class AccountController {

  @PreAuthorize("hasRole('USER')")
  @GetMapping("/account")
  public String getAccountDetails() {
    return "Here are the account details from the DB";
  }
}