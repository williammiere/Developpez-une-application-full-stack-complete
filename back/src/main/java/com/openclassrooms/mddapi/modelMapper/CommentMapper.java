package com.openclassrooms.mddapi.modelMapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.CommentResponseDTO;
import com.openclassrooms.mddapi.model.Comment;

@Component
public class CommentMapper {
    
  public CommentResponseDTO toCommentResponseDTO(Comment comment) {
    CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
    commentResponseDTO.setUser(comment.getUser().getName());
    commentResponseDTO.setContent(comment.getContent());
    return commentResponseDTO;
  }

  public CommentResponseDTO[] toListCommentResponseDTO(Iterable<Comment> comments) {
    CommentResponseDTO[] commentsDTO = java.util.stream.StreamSupport.stream(comments.spliterator(), true)
    .map(this::toCommentResponseDTO)
    .toArray(CommentResponseDTO[]::new);
    return commentsDTO;
  }
}
