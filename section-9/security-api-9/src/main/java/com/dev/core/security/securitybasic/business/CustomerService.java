package com.dev.core.security.securitybasic.business;

import com.dev.core.security.securitybasic.model.entity.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public interface CustomerService extends UserDetailsService {
  Customer findCustomerByEmail(String email);
}