package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ArticleResponseDTO;
import com.openclassrooms.mddapi.dto.CreateArticleDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.modelMapper.ArticleMapper;
import com.openclassrooms.mddapi.modelMapper.UserMapper;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.UserService;

import jakarta.validation.Valid;

/**
 * The article controller.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("api")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @Autowired
  private UserService userService;

  @Autowired
  private ArticleMapper articleMapper;

  @Autowired
  private UserMapper userMapper;

  /**
   * Gets an article by its id.
   *
   * @param id the article's id.
   * @return the article.
   */
  @GetMapping("/article/{id}")
  public ResponseEntity<ArticleDTO> getArticle(@PathVariable int id) {

    ArticleDTO articleDTO = articleMapper.toArticleDTO(articleService.findById(id));
    return ResponseEntity.ok(articleDTO);
    }

    /**
     * Gets all articles.
     *
     * @return the articles.
     */
    @GetMapping("/articles")
    public ResponseEntity<ArticleResponseDTO> getArticles() {

    User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    ArticleResponseDTO articleResponseDTO = new ArticleResponseDTO();
    ArticleDTO[] articles = articleMapper.toListArticleDTO(articleService.findAll(user));
    if (articles == null) {
      articles = new ArticleDTO[0];
    }
    articleResponseDTO.setArticles(articles);
    return ResponseEntity.ok(articleResponseDTO);
    }
    
    /**
     * Creates an article.
     *
     * @param createArticleDTO the article to create.
     * @return the created article.
     */
    @PostMapping("/article/create")
    public ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody CreateArticleDTO createArticleDTO) {

    UserDTO user = userMapper.toUserDTO(userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

    ArticleDTO article = articleMapper.toArticleDTO(articleService.createArticle(user.getId(), createArticleDTO.getTitle(), createArticleDTO.getContent(), createArticleDTO.getTheme()));
    return ResponseEntity.ok(article);

  }
}
