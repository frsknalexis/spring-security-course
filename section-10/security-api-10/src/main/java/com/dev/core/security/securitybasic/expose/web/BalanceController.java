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
public class BalanceController {

  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/balance")
  public String getBalanceDetails() {
    return "Here are the balance details from DB";
  }
}