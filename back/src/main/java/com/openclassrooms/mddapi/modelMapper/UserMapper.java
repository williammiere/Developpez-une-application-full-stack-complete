package com.openclassrooms.mddapi.modelMapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.User;

@Component
public class UserMapper {

  public UserDTO toUserDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setName(user.getName());
    userDTO.setPassword(user.getPassword());
    userDTO.setEmail(user.getEmail());
    userDTO.setAdmin(user.isAdmin());
    userDTO.setCreated_at(user.getCreated_at());
    userDTO.setUpdated_at(user.getUpdated_at());
    return userDTO;
  }
}
