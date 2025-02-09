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

import com.openclassrooms.mddapi.dto.CommentResponseDTO;
import com.openclassrooms.mddapi.dto.CreateCommentDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.modelMapper.CommentMapper;
import com.openclassrooms.mddapi.modelMapper.UserMapper;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("api")
public class CommentController {

  @Autowired
  private CommentService CommentService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private CommentMapper commentMapper;

  @GetMapping("/comments/{id}")
  public ResponseEntity<CommentResponseDTO[]> getComment(@PathVariable int id) {
    Iterable<Comment> comments = CommentService.getComments(id);
    CommentResponseDTO[] CommentResponseDTO = commentMapper.toListCommentResponseDTO(comments);

    return ResponseEntity.ok(CommentResponseDTO);
  }
  

  @PostMapping("/comment/send")
  public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO) {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    UserDTO user = userMapper.toUserDTO(userService.findByEmail(email));

    Comment Comment = CommentService.createComment(createCommentDTO.getArticle_id(), user.getId(), createCommentDTO.getContent());

    CommentResponseDTO CommentResponseDTO = new CommentResponseDTO();
    CommentResponseDTO.setContent(Comment.getContent());

    return ResponseEntity.ok(CommentResponseDTO);
  }
}
