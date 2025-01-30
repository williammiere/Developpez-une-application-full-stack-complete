package com.openclassrooms.mddapi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

@Data
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String register(String email, String name, String password) {
    User existingEmail = userRepository.findByEmail(email);

    if (existingEmail != null) {
      throw new IllegalArgumentException("Email already exists");
    }

    User user = new User();
    user.setEmail(email);
    user.setName(name);
    user.setPassword(passwordEncoder.encode(password));
    user.setCreated_at(LocalDateTime.now());

    userRepository.save(user);

    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder() // Makes the user connected to the eyes of Spring Security
        .username(email)
        .password(user.getPassword())
        .build();

    return jwtService.generateToken(userDetails.getUsername());
  }

  public String login(String email, String password) {
    User user = userRepository.findByEmail(email);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        .username(email)
        .password(user.getPassword())
        .build();

    return jwtService.generateToken(userDetails.getUsername());
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public User findById(int id) {
    User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    return user;
  }

  public User findByEmail(String email) {
    User user = userRepository.findByEmail(email);
    return user;
  }

  public void deleteById(int id) {
    userRepository.deleteById(id);
  }
}