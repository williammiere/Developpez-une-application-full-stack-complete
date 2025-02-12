package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * The DTO class for the comment entity
 * 
 * @see com.openclassrooms.mddapi.model.Comment
 */
@Data
public class CreateCommentDTO {
    @NotNull
    private int article_id;
    @NotBlank
    @Size(min=2)
    private String content;
}
