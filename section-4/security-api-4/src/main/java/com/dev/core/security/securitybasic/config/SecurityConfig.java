package com.dev.core.security.securitybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Some javadoc.
 *
 * @author alexismanuelgutierrezfuentes.
 * @version 0.0.1
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Custom configuration spring security.
   * /account - secured
   * /balance - secured
   * /loans - secured
   * /cards - secured
   * /notices - not secured
   * /contact - not secured
   *
   * @param http http.
   * @throws Exception Exception.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/account").authenticated()
            .antMatchers("/balance").authenticated()
            .antMatchers("/loans").authenticated()
            .antMatchers("/cards").authenticated()
            .antMatchers("/notices", "/contact").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}