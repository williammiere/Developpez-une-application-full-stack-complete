package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The DTO class for the article entity
 * 
 * @see com.openclassrooms.mddapi.model.Article
 */
@Data
public class ArticleDTO {
  @NotNull
  private int id;
  @NotBlank
  private String user;
  @NotBlank
  private String theme;
  @NotBlank
  private String title;
  @NotBlank
  private String content;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
