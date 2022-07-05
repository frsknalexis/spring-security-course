package com.dev.core.security.securitybasic.business.impl;

import com.dev.core.security.securitybasic.business.CustomerService;
import com.dev.core.security.securitybasic.model.SecurityCustomer;
import com.dev.core.security.securitybasic.model.entity.Customer;
import com.dev.core.security.securitybasic.repository.CustomerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return Optional.ofNullable(customerRepository.findByEmail(username))
            .map(SecurityCustomer::new)
            .orElseThrow(() ->
                    new UsernameNotFoundException("User details not found for the user: "
                            + username));
  }

  @Override
  public Customer findCustomerByEmail(String email) {
    return null;
  }
}