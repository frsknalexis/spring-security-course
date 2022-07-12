package com.dev.core.auth.authservice.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.dev.core.auth.authservice.security.filter.CustomAuthenticationFilter;
import com.dev.core.auth.authservice.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;

  private final BCryptPasswordEncoder bcryptPasswordEncoder;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter =
            new CustomAuthenticationFilter(authenticationManagerBean());
    customAuthenticationFilter.setFilterProcessesUrl("/auth-service/v1/login");

    http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/auth-service/v1/login/**",
                    "/auth-service/v1/token/refresh/**")
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/auth-service/v1/users/**")
            .hasAnyAuthority("ROLE_USER")
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth-service/v1/user/save/**")
            .hasAnyAuthority("ROLE_ADMIN")
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilter(customAuthenticationFilter)
            .addFilterBefore(new CustomAuthorizationFilter(),
                    UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}