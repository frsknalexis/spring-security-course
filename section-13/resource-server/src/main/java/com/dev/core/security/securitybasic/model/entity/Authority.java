package com.dev.core.security.securitybasic.model.entity;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "authorities")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Authority {

  @Id
  @GeneratedValue(strategy = AUTO)
  @Column(name = "id")
  private Integer authorityId;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;
}