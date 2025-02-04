package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleDTO {
  @NotBlank
  private int id;
  @NotBlank
  private int user;
  @NotBlank
  private String theme;
  @NotBlank
  private String title;
  @NotBlank
  private String content;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
