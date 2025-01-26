package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SingupRequestDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
}
