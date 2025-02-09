package com.openclassrooms.mddapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.openclassrooms.mddapi.service.CustomUserDetailService;

import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Autowired
  private JwtAuthenticationFilter authenticationFilter;

  @Autowired
  @Bean // Authentication provider that authenticates a user using customUserDetailsService
  public DaoAuthenticationProvider authenticationProvider() { 
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(customUserDetailService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

 // The @Bean annotation says that Spring will manage the object and that it can be injected into other classes
  @Bean // Authentication manager that returns an authentication manager
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .cors(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authorize -> authorize
        // No auth needed on :
        .requestMatchers("/api/auth/login",
          "/api/auth/register",
          "favicon.ico").permitAll()
        .anyRequest().authenticated()
      )
      .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
          (request, response, exception) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
          }));
    return http.build();
  }
}
