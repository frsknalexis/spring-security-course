package com.dev.core.security.securitybasic.expose.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  @GetMapping("/account")
  public String getAccountDetails() {
    return "Here are the account details from the DB";
  }
}