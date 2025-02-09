package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class CommentResponseDTO {
    private String user;
    private String content;
}
