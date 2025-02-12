package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.dto.SingupRequestDTO;
import com.openclassrooms.mddapi.dto.TokenResponseDTO;
import com.openclassrooms.mddapi.dto.UserAuthResponseDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.modelMapper.UserMapper;
import com.openclassrooms.mddapi.service.JwtService;
import com.openclassrooms.mddapi.service.UserService;

import jakarta.validation.Valid;

/**
 * The User controller.
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;

    /**
     * Gets a user by its id.
     *
     * @param id the user's id.
     * @return the user.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserAuthResponseDTO> getUser(@PathVariable int id) {

        UserDTO user = userMapper.toUserDTO(userService.findById(id));

        UserAuthResponseDTO userResponse = new UserAuthResponseDTO();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getName());
        userResponse.setCreated_at(user.getCreated_at());
        userResponse.setUpdated_at(user.getUpdated_at());

        return ResponseEntity.ok(userResponse);
    }

    /**
     * Registers a new user.
     *
     * @param signUpRequest the registration details.
     * @return the registered user's authentication details.
     */
    @PostMapping("/auth/register")
    public ResponseEntity<TokenResponseDTO> register(
            @Valid @RequestBody SingupRequestDTO singupRequestDTO) {
        String jwtToken = userService.register(singupRequestDTO.getEmail(), singupRequestDTO.getUsername(), singupRequestDTO.getPassword());
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setToken(jwtToken);
        tokenResponseDTO.setEmail(singupRequestDTO.getEmail());
        tokenResponseDTO.setUsername(userService.findByEmail(singupRequestDTO.getEmail()).getName());
        tokenResponseDTO.setId(userService.findByEmail(singupRequestDTO.getEmail()).getId());
        tokenResponseDTO.setAdmin(userService.findByEmail(singupRequestDTO.getEmail()).isAdmin());
        return ResponseEntity.ok(tokenResponseDTO);
    }

    /**
     * Logs in a user.
     *
     * @param loginRequestDTO the login details.
     * @return the logged in user's authentication details.
     */
    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        String jwtToken = userService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setToken(jwtToken);
        tokenResponseDTO.setEmail(loginRequestDTO.getEmail());
        tokenResponseDTO.setUsername(userService.findByEmail(loginRequestDTO.getEmail()).getName());
        tokenResponseDTO.setId(userService.findByEmail(loginRequestDTO.getEmail()).getId());
        tokenResponseDTO.setAdmin(userService.findByEmail(loginRequestDTO.getEmail()).isAdmin());
        return ResponseEntity.ok(tokenResponseDTO);
    }

    /**
     * Gets the current user.
     *
     * @return the current user.
     */
    @GetMapping("/auth/me")
    public ResponseEntity<UserAuthResponseDTO> me() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO user = userMapper.toUserDTO(userService.findByEmail(email));

        UserAuthResponseDTO userResponse = new UserAuthResponseDTO();
        userResponse.setId(user.getId());
        userResponse.setEmail(email);
        userResponse.setUsername(user.getName());
        userResponse.setCreated_at(user.getCreated_at());
        userResponse.setUpdated_at(user.getUpdated_at());

        return ResponseEntity.ok(userResponse);

    }

    /**
     * Updates the current user.
     *
     * @param UserUpdateDTO the updated user details.
     * @return the updated user's authentication details.
     */
    @PutMapping("/auth/update")
    public ResponseEntity<TokenResponseDTO> update(
            @Valid @RequestBody UserUpdateDTO UserUpdateDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO user = userMapper.toUserDTO(userService.findByEmail(email));
        if(!UserUpdateDTO.getEmail().equals(user.getEmail())) {
            if(userService.findByEmail(UserUpdateDTO.getEmail()) != null) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(UserUpdateDTO.getEmail());
        }
        user.setName(UserUpdateDTO.getUsername());
        userService.save(userMapper.toUser(user));
        TokenResponseDTO userResponse = new TokenResponseDTO();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getName());
        userResponse.setToken(jwtService.generateToken(user.getEmail()));
        return ResponseEntity.ok(userResponse);
    }

}
