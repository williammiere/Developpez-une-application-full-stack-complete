package com.openclassrooms.mddapi.modelMapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.CommentResponseDTO;
import com.openclassrooms.mddapi.model.Comment;

/**
 * Mapper for the Comment entity
 */
@Component
public class CommentMapper {
    
  /**
   * Converts a Comment entity to a CommentResponseDTO
   * @param comment the Comment entity
   * @return the CommentResponseDTO
   */
  public CommentResponseDTO toCommentResponseDTO(Comment comment) {
    CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
    commentResponseDTO.setUser(comment.getUser().getName());
    commentResponseDTO.setContent(comment.getContent());
    return commentResponseDTO;
  }

  /**
   * Converts an iterable of Comment entities to an array of CommentResponseDTOs
   * @param comments the iterable of Comment entities
   * @return the array of CommentResponseDTOs
   */
  public CommentResponseDTO[] toListCommentResponseDTO(Iterable<Comment> comments) {
    CommentResponseDTO[] commentsDTO = java.util.stream.StreamSupport.stream(comments.spliterator(), true)
    .map(this::toCommentResponseDTO)
    .toArray(CommentResponseDTO[]::new);
    return commentsDTO;
  }
}
