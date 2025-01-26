package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserAuthResponseDTO {
  private int id;
  private String name;
  private String email;
  private boolean admin;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
