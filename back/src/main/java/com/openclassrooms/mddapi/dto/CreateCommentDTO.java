package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCommentDTO {
    @NotBlank
    private int rental_id;
    @NotBlank
    private int user_id;
    @NotBlank
    @Size(min=1)
    private String content;
}
