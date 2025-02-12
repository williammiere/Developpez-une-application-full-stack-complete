package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * The DTO class for the theme entity
 * 
 * @see com.openclassrooms.mddapi.model.Theme
 */
@Data
public class ThemeDTO {
    @NotNull
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
