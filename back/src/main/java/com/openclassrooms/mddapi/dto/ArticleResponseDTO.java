package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * The DTO class for the article entity
 * 
 * @see com.openclassrooms.mddapi.model.Article
 */
@Data
public class ArticleResponseDTO {

    private ArticleDTO[] articles;
    
}
