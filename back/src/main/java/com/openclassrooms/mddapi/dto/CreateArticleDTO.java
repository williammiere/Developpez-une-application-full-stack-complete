package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateArticleDTO {
    @NotNull
    @Size(min=1)
    private String title;
    @NotBlank
    @Size(min=1)
    private String content;
    @NotNull
    @Size(min=1)
    private String theme;
    @NotBlank
    private int author;
}
