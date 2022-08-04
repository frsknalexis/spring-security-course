package com.dev.core.security.jwt.securityservice.business.processor;

import com.dev.core.security.jwt.securityservice.model.api.response.CustomerResponse;
import com.dev.core.security.jwt.securityservice.model.entity.Authority;
import com.dev.core.security.jwt.securityservice.model.entity.Customer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Component
public class CustomerProcessor {

  /**
   * Method that build CustomerResponse.
   *
   * @param customer customer.
   * @return {@link CustomerResponse} CustomerResponse.
   */
  public CustomerResponse buildCustomerResponse(Customer customer) {
    return CustomerResponse.builder()
            .customerId(customer.getCustomerId())
            .name(customer.getName())
            .email(customer.getEmail())
            .mobileNumber(customer.getMobileNumber())
            .roles(buildRolesForCustomer(customer.getAuthorities()))
            .createDate(customer.getCreateDate())
            .build();
  }

  private List<String> buildRolesForCustomer(Set<Authority> authoritiesList) {
    return Optional.ofNullable(authoritiesList)
            .map(authorities -> authorities.stream()
                    .map(Authority::getName)
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList()))
            .orElse(Collections.emptyList());
  }
}