package com.openclassrooms.mddapi.modelMapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.User;

/**
 * Mapper for the User entity
 */
@Component
public class UserMapper {

  /**
   * Converts a User entity to a UserDTO
   * @param user the User entity
   * @return the UserDTO
   */
  public UserDTO toUserDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setName(user.getName());
    if(user.getPassword() != null){
      userDTO.setPassword(user.getPassword());
    }
    userDTO.setEmail(user.getEmail());
    userDTO.setAdmin(user.isAdmin());
    userDTO.setCreated_at(user.getCreated_at());
    userDTO.setUpdated_at(user.getUpdated_at());
    return userDTO;
  }

  /**
   * Converts an array of User entities to an array of UserDTOs
   * @param users the array of User entities
   * @return the array of UserDTOs
   */
  public User toUser(UserDTO userDTO) {
    User user = new User();
    user.setId(userDTO.getId());
    user.setName(userDTO.getName());
    user.setPassword(userDTO.getPassword());
    user.setEmail(userDTO.getEmail());
    user.setAdmin(userDTO.isAdmin());
    user.setCreated_at(userDTO.getCreated_at());
    user.setUpdated_at(userDTO.getUpdated_at());
    return user;
  }
}
