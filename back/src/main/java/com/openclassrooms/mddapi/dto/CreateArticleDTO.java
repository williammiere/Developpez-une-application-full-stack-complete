package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateArticleDTO {
    @NotBlank
    @Size(min=3)
    private String title;
    @NotBlank
    @Size(min=3)
    private String content;
    @NotBlank
    private int theme_id;
}
