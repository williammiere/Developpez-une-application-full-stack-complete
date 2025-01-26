package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentResponseDTO;
import com.openclassrooms.mddapi.dto.CreateCommentDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.modelMapper.UserMapper;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(summary = "Create a new Comment")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Comment created"),
          @ApiResponse(responseCode = "400", description = "Bad request")
  })
  @PostMapping("/Comments")
  public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO) {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    UserDTO user = userMapper.toUserDTO(userService.findByEmail(email));

    Comment Comment = CommentService.createComment(createCommentDTO.getRental_id(), user.getId(), createCommentDTO.getContent());

    CommentResponseDTO CommentResponseDTO = new CommentResponseDTO();
    CommentResponseDTO.setContent(Comment.getContent());

    return ResponseEntity.ok(CommentResponseDTO);
  }
}
