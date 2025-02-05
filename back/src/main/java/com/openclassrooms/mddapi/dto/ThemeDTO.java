package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ThemeDTO {
    @NotBlank
    private int id;
    @NotBlank
    @Size(min=1)
    private String name;
    @NotBlank
    @Size(min=1)
    private String description;
    @NotBlank
    private boolean subscribed;
}
