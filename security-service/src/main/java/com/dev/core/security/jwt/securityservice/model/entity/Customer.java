package com.dev.core.security.jwt.securityservice.model.entity;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

  @Id
  @GeneratedValue(strategy = AUTO)
  @Column(name = "customer_id")
  private Integer customerId;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "mobile_number")
  private String mobileNumber;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  @Column(name = "create_date")
  private LocalDate createDate;

  @OneToMany(mappedBy = "customer", fetch = EAGER)
  private Set<Authority> authorities;
}