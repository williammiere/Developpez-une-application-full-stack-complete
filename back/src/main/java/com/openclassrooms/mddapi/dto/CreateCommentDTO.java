package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCommentDTO {
    @NotBlank
    private int rental_id;
    @NotBlank
    private int user_id;
    @NotBlank
    private String content;
}
