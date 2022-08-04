package com.dev.core.security.jwt.securityservice.business;

import com.dev.core.security.jwt.securityservice.model.api.response.CustomerResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
public interface CustomerService extends UserDetailsService {

  CustomerResponse findCustomerByEmail() throws Exception;
}