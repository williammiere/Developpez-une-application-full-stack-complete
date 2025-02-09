package com.openclassrooms.mddapi.service;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.UserTheme;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserThemeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserThemeRepository userThemeRepository;

    @Autowired
    private UserService userService;

    public Article[] findAll(User user) {
        Iterable<UserTheme> userThemes = userThemeRepository.findByUser(user);
        Theme[] themes = StreamSupport.stream(userThemes.spliterator(), true).map(UserTheme::getTheme).toArray(Theme[]::new);
        Iterable<Article> articles = articleRepository.findByThemeIn(themes);
        return StreamSupport.stream(articles.spliterator(), true).toArray(Article[]::new);
    }

    public Article findById(int id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found"));
        return article;
    }

    public Article createArticle(int user_id, String title, String content, String theme) {
        Article article = new Article();
        article.setUser(userService.findById(user_id));
        article.setTitle(title);
        article.setContent(content);
        
        if(themeRepository.findByTitle(theme) == null) {
            Theme newTheme = new Theme();
            newTheme.setTitle(theme);
            newTheme.setDescription(theme);
            themeRepository.save(newTheme);
            article.setTheme(newTheme);
        }else{
            article.setTheme(themeRepository.findByTitle(theme));
        }


        return articleRepository.save(article);
    }

    public Article updateArticle(int id, int user_id, String title, String content) {
        
        Article article = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found"));
        if (article.getUser().getId() != user_id) {
            throw new IllegalArgumentException("You are not the owner of this article");
        }

        article.setUser(userService.findById(user_id));
        article.setTitle(title);
        article.setContent(content);

        return articleRepository.save(article);
    }
}