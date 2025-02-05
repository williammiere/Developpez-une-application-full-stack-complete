package com.openclassrooms.mddapi.service;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.UserTheme;
import com.openclassrooms.mddapi.model.UserThemeId;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.UserThemeRepository;

import lombok.Data;

@Data
@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserThemeRepository userThemeRepository;

    public Theme[] findAll() {
        Iterable<Theme> themes = themeRepository.findAll();
        return StreamSupport.stream(themes.spliterator(), false).toArray(Theme[]::new);
    }

    public boolean subscribe(int themeId, int userId) {
    Theme theme = themeRepository.findById(themeId).orElse(null);
    User user = userRepository.findById(userId).orElse(null);
    if (user == null || theme == null) {
        return false;
    }
    UserThemeId id = new UserThemeId();
    id.setUserId(userId);
    id.setThemeId(themeId);
    
    if(userThemeRepository.existsById(id)){
        return true;
    }

    UserTheme userTheme = new UserTheme();
    userTheme.setId(id);
    userTheme.setUser(user);
    userTheme.setTheme(theme);
    userThemeRepository.save(userTheme);
    return true;
}

public boolean unsubscribe(int themeId, int userId) {
    UserThemeId id = new UserThemeId();
    id.setUserId(userId);
    id.setThemeId(themeId);
    if (!userThemeRepository.existsById(id)) {
        return false;
    }
    userThemeRepository.deleteById(id);
    return true;
}

public boolean isSubscribed(int themeId, int userId) {
    UserThemeId id = new UserThemeId();
    id.setUserId(userId);
    id.setThemeId(themeId);
    return userThemeRepository.existsById(id);
}

public String[] getSubscribedThemes(int userId) {
    Iterable<UserTheme> userThemes = userThemeRepository.findAll();
    return StreamSupport.stream(userThemes.spliterator(), true)
        .filter(userTheme -> userTheme.getUser().getId() == userId)
        .map(userTheme -> userTheme.getTheme().getTitle())
        .toArray(String[]::new);
    }
}

