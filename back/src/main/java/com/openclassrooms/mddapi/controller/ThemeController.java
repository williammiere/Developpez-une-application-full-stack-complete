package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.dto.ThemeResponseDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.modelMapper.ThemeMapper;
import com.openclassrooms.mddapi.modelMapper.UserMapper;
import com.openclassrooms.mddapi.service.ThemeService;
import com.openclassrooms.mddapi.service.UserService;

/**
 * The Theme controller.
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("api")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ThemeMapper themeMapper;

    /**
     * Gets all themes.
     *
     * @return the themes.
     */
    @GetMapping("/themes")
    public ResponseEntity<ThemeResponseDTO> getThemes() {

    ThemeResponseDTO themeResponseDTO = new ThemeResponseDTO();

    ThemeDTO[] themes = themeMapper.toListThemeDTO(themeService.findAll());

    if (themes == null) {
      themes = new ThemeDTO[0];
    }
    themeResponseDTO.setThemes(themes);
    int userId = userMapper.toUserDTO(userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())).getId();

    for (ThemeDTO theme : themes) {
      theme.setSubscribed(themeService.isSubscribed(theme.getId(), userId));
    }

    return ResponseEntity.ok(themeResponseDTO);
    }

    /**
     * Gets all subscribed themes for the current user.
     *
     * @return the subscribed themes.
     */
    @GetMapping("/themes/subscribed")
    public ResponseEntity<ThemeDTO[]> getSubscribedThemes() {

      UserDTO userDTO = userMapper.toUserDTO(userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

      return ResponseEntity.ok(themeMapper.toListThemeDTO(themeService.getSubscribedThemes(userDTO.getId())));
    }
    
    /**
     * Subscribes to a theme.
     *
     * @param themeId the theme's id.
     * @return true if the subscription was successful, false otherwise.
     */
    @PostMapping("/theme/subscribe/{themeId}")
    public ResponseEntity<Boolean> subscribe(@PathVariable int themeId) {

      UserDTO userDTO = userMapper.toUserDTO(userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

      return ResponseEntity.ok(themeService.subscribe(themeId, userDTO.getId()));
    }

    /**
     * Unsubscribes from a theme.
     *
     * @param themeId the theme's id.
     * @return true if the unsubscription was successful, false otherwise.
     */
    @PostMapping("/theme/unsubscribe/{themeId}")
    public ResponseEntity<Boolean> unsubscribe(@PathVariable int themeId) {

      UserDTO userDTO = userMapper.toUserDTO(userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

      return ResponseEntity.ok(themeService.unsubscribe(themeId, userDTO.getId()));
    }
    
}
