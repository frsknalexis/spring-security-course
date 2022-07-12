package com.dev.core.auth.authservice.model.entity;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

/**
 *
 * @author alexismanuelgutierrezfuentes
 * @version 1.0.0
 * @since 30/05/2022
 */
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    private String name;

    private String username;

    private String password;

    @ManyToMany(fetch = EAGER)
    private List<Role> roles = new ArrayList<>();
}