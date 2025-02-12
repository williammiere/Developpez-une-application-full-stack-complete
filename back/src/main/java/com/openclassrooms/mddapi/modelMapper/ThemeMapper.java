package com.openclassrooms.mddapi.modelMapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.model.Theme;

/**
 * Mapper for the Theme entity
 */
@Component
public class ThemeMapper {
    
  /**
   * Converts a Theme entity to a ThemeDTO
   * @param theme the Theme entity
   * @return the ThemeDTO
   */
  public ThemeDTO toThemeDTO(Theme theme) {
    ThemeDTO themeDTO = new ThemeDTO();
    themeDTO.setId(theme.getId());
    themeDTO.setName(theme.getTitle());
    themeDTO.setDescription(theme.getDescription());
    return themeDTO;
  }

  /**
   * Converts an array of Theme entities to an array of ThemeDTOs
   * @param themes the array of Theme entities
   * @return the array of ThemeDTOs
   */
  public ThemeDTO[] toListThemeDTO(Theme[] themes) {
    ThemeDTO[] themesDTO = new ThemeDTO[themes.length];
    for (int i = 0; i < themes.length; i++) {
      themesDTO[i] = toThemeDTO(themes[i]);
    }
    return themesDTO;
  }
}
