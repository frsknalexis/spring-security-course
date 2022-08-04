package com.dev.core.security.jwt.securityservice.business.impl;

import static com.dev.core.security.jwt.securityservice.security.utils.SecurityUtils.getCurrentUsername;

import com.dev.core.security.jwt.securityservice.business.CustomerService;
import com.dev.core.security.jwt.securityservice.business.processor.CustomerProcessor;
import com.dev.core.security.jwt.securityservice.model.api.response.CustomerResponse;
import com.dev.core.security.jwt.securityservice.repository.CustomerRepository;
import com.dev.core.security.jwt.securityservice.security.model.SecurityCustomer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  private final CustomerProcessor customerProcessor;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("Authenticating user {}", username);
    return Optional.ofNullable(customerRepository.findByEmail(username))
            .map(SecurityCustomer::new)
            .orElseThrow(() ->
                    new UsernameNotFoundException("User with email: " + username + " not found"));
  }

  @Override
  public CustomerResponse findCustomerByEmail() throws Exception {
    return Optional.ofNullable(customerRepository.findByEmail(getCurrentUsername()))
            .map(customerProcessor::buildCustomerResponse)
            .orElseThrow(() ->
                    new Exception("Customer details not found for the specified email"));
  }
}