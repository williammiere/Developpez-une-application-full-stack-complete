package com.openclassrooms.mddapi.modelMapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.model.Article;

@Component
public class ArticleMapper {

  public ArticleDTO toArticleDTO(Article article) {
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setId(article.getId());
    articleDTO.setUser_id(article.getUser().getId());
    articleDTO.setTitle(article.getTitle());
    articleDTO.setContent(article.getContent());
    articleDTO.setCreated_at(article.getCreated_at());
    articleDTO.setUpdated_at(article.getUpdated_at());
    return articleDTO;
  }

  public ArticleDTO[] toListArticleDTO(Article[] articles) {
    ArticleDTO[] articlesDTO = Arrays.stream(articles)
      .map(this::toArticleDTO)
      .toArray(ArticleDTO[]::new);

      return articlesDTO;
  }

}
