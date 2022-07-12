package com.dev.core.oauth.security.oauthsecurityapi.expose.web;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Controller
public class SecureController {

  @GetMapping("/")
  public String main(OAuth2AuthenticationToken token) {
    System.out.println(token.getPrincipal());
    return "secure.html";
  }
}