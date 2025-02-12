package com.openclassrooms.mddapi.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openclassrooms.mddapi.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * The JWT authentication filter.
 * 
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserDetailsService userDetailsService;


  /**
   * Filters the request.
   * 
   * @param request the request.
   * @param response the response.
   * @param filterChain the filter chain.
   * @throws ServletException if an error occurs.
   * @throws IOException if an error occurs.
   */
  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    log.info("authHeader : {}", authHeader);

    // Look for a bearer token
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      // Passes through our filter
      filterChain.doFilter(request, response);
      return;
    }

    // Retrieves the JWT.
    String jwt = authHeader.substring(7);
    // Retrieves the right username from the token.
    String userEmail = jwtService.extractUsername(jwt);
    log.info("userEmail : {}", userEmail);

    // Retrieves the current authentication.
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("authentication : {}", authentication);

    // If username valid and not already authenticated :
    if (userEmail != null && authentication == null) {
      // Gets user details.
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

      if (jwtService.isTokenValid(jwt, userDetails)) {
        // Creates the authentication token with the user details.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails, // User details.
          null, // Credentials (not needed anymore).
          userDetails.getAuthorities() // User roles (no roles in this context).
        );

        // Adds other informations like the IP address or session ID (not used here in favour of JWT) to the token.
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // Sets the security context.
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
      // Allow the request.
      log.info("authentication : {}", authentication);
      filterChain.doFilter(request, response);
    }
  }
}
