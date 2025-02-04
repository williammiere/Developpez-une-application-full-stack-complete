package com.openclassrooms.mddapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ArticleResponseDTO;
import com.openclassrooms.mddapi.dto.CreateArticleDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.modelMapper.ArticleMapper;
import com.openclassrooms.mddapi.modelMapper.UserMapper;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


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

  @Operation(summary = "Get article by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Article found"),
          @ApiResponse(responseCode = "404", description = "Article not found")
  })
  @GetMapping("/article/{id}")
  public ResponseEntity<ArticleDTO> getArticle(@PathVariable int id) {

    ArticleDTO articleDTO = articleMapper.toArticleDTO(articleService.findById(id));
    return ResponseEntity.ok(articleDTO);
    }

    @Operation(summary = "Get all article")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Articles found"),
        @ApiResponse(responseCode = "404", description = "No article found")
    })
    @GetMapping("/articles")
    public ResponseEntity<ArticleResponseDTO> getArticles() {

    ArticleResponseDTO articleResponseDTO = new ArticleResponseDTO();
    ArticleDTO[] articles = articleMapper.toListArticleDTO(articleService.findAll());
    if (articles == null) {
      articles = new ArticleDTO[0];
    }
    articleResponseDTO.setArticles(articles);
    return ResponseEntity.ok(articleResponseDTO);
    }
    
    @Operation(summary = "Create a new article")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Article created"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/article/create")
    public ResponseEntity<ArticleDTO> createArticle(@Valid @ModelAttribute CreateArticleDTO createArticleDTO) throws IOException {

    UserDTO user = userMapper.toUserDTO(userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

    ArticleDTO article = articleMapper.toArticleDTO(articleService.createArticle(user.getId(), createArticleDTO.getTitle(), createArticleDTO.getContent(), createArticleDTO.getTheme()));
    return ResponseEntity.ok(article);

  }
}
