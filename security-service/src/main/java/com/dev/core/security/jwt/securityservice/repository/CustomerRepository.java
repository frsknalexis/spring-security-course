package com.dev.core.security.jwt.securityservice.repository;

import com.dev.core.security.jwt.securityservice.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  Customer findByEmail(String email);
}