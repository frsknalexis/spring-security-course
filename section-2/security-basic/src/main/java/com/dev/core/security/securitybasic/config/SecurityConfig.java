package com.dev.core.security.securitybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("DEV_ADMIN")
            .password("123456")
            .authorities("ADMIN")
            .and()
            .withUser("DEV_USER")
            .password("123456")
            .authorities("READ")
            .and()
            .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}