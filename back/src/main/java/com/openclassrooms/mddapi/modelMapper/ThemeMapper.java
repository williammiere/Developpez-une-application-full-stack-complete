package com.openclassrooms.mddapi.modelMapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.model.Theme;

@Component
public class ThemeMapper {
    
  public ThemeDTO toThemeDTO(Theme theme) {
    ThemeDTO themeDTO = new ThemeDTO();
    themeDTO.setId(theme.getId());
    themeDTO.setName(theme.getTitle());
    themeDTO.setDescription(theme.getDescription());
    return themeDTO;
  }

  public ThemeDTO[] toListThemeDTO(Theme[] themes) {
    ThemeDTO[] themesDTO = new ThemeDTO[themes.length];
    for (int i = 0; i < themes.length; i++) {
      themesDTO[i] = toThemeDTO(themes[i]);
    }
    return themesDTO;
  }
}
