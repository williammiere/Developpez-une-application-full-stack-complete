package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * The DTO class for the article entity
 * 
 * @see com.openclassrooms.mddapi.model.Article
 */
@Data
public class CreateArticleDTO {
    @NotBlank
    @Size(min=2)
    private String title;
    @NotBlank
    @Size(min=2)
    private String content;
    @NotBlank
    @Size(min=2)
    private String theme;
    @NotNull
    private int author;
}
