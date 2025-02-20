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

/**
 * The service class for the user entity
 */
@Data
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Register a user
   * @param email the email of the user
   * @param name the name of the user
   * @param password the password of the user
   * @return the token
   */
  public String register(String email, String name, String password) {
    User existingEmail = userRepository.findByEmail(email);

    if (existingEmail != null) {
      throw new IllegalArgumentException("L'email existe déjà");
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

  /**
   * Login a user
   * @param email the email of the user
   * @param password the password of the user
   * @return the token
   */
  public String login(String email, String password) {
    User user = userRepository.findByEmail(email);

    if(user == null) {
      throw new IllegalArgumentException("Utilisateur non trouvé");
    }

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("Identifiants invalides");
    }

    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        .username(email)
        .password(user.getPassword())
        .build();

    return jwtService.generateToken(userDetails.getUsername());
  }

  /**
   * Save a user
   * @param user the user to save
   * @return the saved user
   */
  public User save(User user) {
    return userRepository.save(user);
  }

  /**
   * Find a user by its id
   * @param id the id of the user
   * @return the user
   */
  public User findById(int id) {
    return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
  }

  /**
   * Find a user by its email
   * @param email the email of the user
   * @return the user
   */
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  /**
   * Delete a user by its id
   * @param id the id of the user
   */
  public void deleteById(int id) {
    userRepository.deleteById(id);
  }

  /**
   * Check if the password is correct
   * @param email the email of the user
   * @param password the password to check
   * @return true if the password is correct, false otherwise
   */
  public boolean passwordCheck(String email, String password) {
    User user = userRepository.findByEmail(email);
    return passwordEncoder.matches(password, user.getPassword());
  }
}