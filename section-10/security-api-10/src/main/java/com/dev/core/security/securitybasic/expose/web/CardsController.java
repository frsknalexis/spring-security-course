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
public class CardsController {

  @GetMapping("/cards")
  public String getCardDetails() {
    return "Here are the card details from the DB";
  }
}