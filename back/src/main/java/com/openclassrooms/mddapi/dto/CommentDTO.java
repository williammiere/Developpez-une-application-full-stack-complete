package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDTO {
  private int id;
  @NotBlank
  private int article_id;
  @NotBlank
  private int user_id;
  @NotBlank
  private String content;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
