package com.openclassrooms.mddapi.dto;

import lombok.Data;


@Data
public class TokenResponseDTO {
    private String token;
    private String username;
    private String email;
    private int id;
    private boolean admin;
}
