package com.dev.core.auth.authservice.business.impl;

import com.dev.core.auth.authservice.business.UserService;
import com.dev.core.auth.authservice.model.entity.Role;
import com.dev.core.auth.authservice.model.entity.User;
import com.dev.core.auth.authservice.repository.RoleRepository;
import com.dev.core.auth.authservice.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author alexismanuelgutierrezfuentes
 * @version 1.0.0
 * @since 03/06/2022
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = Optional.ofNullable(userRepository.findByUsername(username))
           .orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));

    List<SimpleGrantedAuthority> authorities = user.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPassword(), authorities);
  }

  @Override
  public User saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    return roleRepository.save(role);
  }

  @Override
  public void addRoleToUser(String username, String roleName) {
    User user = userRepository.findByUsername(username);
    Role role = roleRepository.findByName(roleName);
    user.getRoles().add(role);
  }

  @Override
  public User getUser(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }
}