package com.dev.core.security.securitybasic.expose.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@RestController
public class LoansController {

  @GetMapping("/loans")
  public String getLoanDetails() {
    return "Here are the loan details from the DB";
  }
}