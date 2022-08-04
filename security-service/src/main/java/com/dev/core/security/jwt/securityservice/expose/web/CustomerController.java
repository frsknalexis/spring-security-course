package com.dev.core.security.jwt.securityservice.expose.web;

import com.dev.core.security.jwt.securityservice.business.CustomerService;
import com.dev.core.security.jwt.securityservice.model.api.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  /**
   * Some javadoc.
   *
   * @author alexismanuelgutierrezfuentes.
   * @return CustomerResponse
   */
  @GetMapping("/customer-details")
  public CustomerResponse getCustomer()
      throws Exception {
    return customerService.findCustomerByEmail();
  }
}