package com.openclassrooms.mddapi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;

/**
 * The service class for the comment entity
 */
@Service
public class CommentService {

  @Autowired
  private CommentRepository messageRepository;

  @Autowired
  private ArticleService ArticleService;

  @Autowired
  private UserService userService;

  /**
   * Get all comments for an article
   * @param id the id of the article
   * @return an iterable of comments
   */
  public Iterable<Comment> getComments(int id) {
    Iterable<Comment> comments = messageRepository.findAllByArticleId(id);
    if (comments == null) {
      throw new IllegalArgumentException("No comment found");
    }
    return comments;
  }

  /**
   * Create a comment
   * @param article_id the id of the article
   * @param user_id the id of the user
   * @param content the content of the comment
   * @return the comment
   */
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

  /**
   * Save a comment
   * @param message the comment to save
   * @return the saved comment
   */
  public Comment saveComment(Comment message) {
    return messageRepository.save(message);
  }
}