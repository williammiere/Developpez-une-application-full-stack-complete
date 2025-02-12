package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * The DTO class for the comment entity
 * 
 * @see com.openclassrooms.mddapi.model.Comment
 */
@Data
public class CommentResponseDTO {
    private String user;
    private String content;
}
