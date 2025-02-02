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
import com.openclassrooms.mddapi.modelMapper.UserMapper;
import com.openclassrooms.mddapi.service.JwtService;
import com.openclassrooms.mddapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
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

    @Operation(summary = "Register a new user", description = "Register a new user with email, name and password and send back a JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
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

    @Operation(summary = "Login a user", description = "Login a user with email and password and send back a JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged in"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
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

    @Operation(summary = "Get the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
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

    @Operation(summary = "Update email and username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/auth/update")
    public ResponseEntity<TokenResponseDTO> update(
            @Valid @RequestBody SingupRequestDTO singupRequestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO user = userMapper.toUserDTO(userService.findByEmail(email));
        user.setEmail(singupRequestDTO.getEmail());
        user.setName(singupRequestDTO.getUsername());
        userService.save(userMapper.toUser(user));
        TokenResponseDTO userResponse = new TokenResponseDTO();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getName());
        userResponse.setToken(jwtService.generateToken(user.getEmail()));
        return ResponseEntity.ok(userResponse);
    }

}
