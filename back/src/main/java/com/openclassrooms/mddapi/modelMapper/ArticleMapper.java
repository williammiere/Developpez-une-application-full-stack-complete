package com.openclassrooms.mddapi.modelMapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.model.Article;

/**
 * Mapper for the Article entity
 */
@Component
public class ArticleMapper {

  /**
   * Converts an Article entity to an ArticleDTO
   * @param article the Article entity
   * @return the ArticleDTO
   */
  public ArticleDTO toArticleDTO(Article article) {
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setId(article.getId());
    articleDTO.setUser(article.getUser().getName());
    articleDTO.setTitle(article.getTitle());
    articleDTO.setContent(article.getContent());
    articleDTO.setTheme(article.getTheme().getTitle());
    articleDTO.setCreated_at(article.getCreated_at());
    articleDTO.setUpdated_at(article.getUpdated_at());
    return articleDTO;
  }

  /**
   * Converts an array of Article entities to an array of ArticleDTOs
   * @param articles the array of Article entities
   * @return the array of ArticleDTOs
   */
  public ArticleDTO[] toListArticleDTO(Article[] articles) {
    ArticleDTO[] articlesDTO = Arrays.stream(articles)
      .map(this::toArticleDTO)
      .toArray(ArticleDTO[]::new);

      return articlesDTO;
  }

}
