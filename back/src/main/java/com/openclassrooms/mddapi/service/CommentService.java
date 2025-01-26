package com.openclassrooms.mddapi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;

@Service
public class CommentService {

  @Autowired
  private CommentRepository messageRepository;

  @Autowired
  private ArticleService ArticleService;

  @Autowired
  private UserService userService;

  public Comment createComment(int article_id, int user_id, String content) {
    Article Article = ArticleService.findById(article_id);
    User user = userService.findById(user_id);

    Comment newComment = new Comment();
    newComment.setArticle(Article);
    newComment.setUser(user);
    newComment.setContent(content);
    newComment.setCreated_at(LocalDateTime.now());

    return messageRepository.save(newComment);
  }

  public Comment saveComment(Comment message) {
    return messageRepository.save(message);
  }
}