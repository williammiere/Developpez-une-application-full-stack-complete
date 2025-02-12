package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * The DTO class for the theme response
 */
@Data
public class ThemeResponseDTO {
    private ThemeDTO[] themes;
}
