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

/**
 * The service class for the theme entity
 */
@Data
@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserThemeRepository userThemeRepository;

    /**
     * Find all themes
     *
     * @return an array of themes
     */
    public Theme[] findAll() {
        Iterable<Theme> themes = themeRepository.findAll();
        return StreamSupport.stream(themes.spliterator(), false).toArray(Theme[]::new);
    }

    /**
     * Find a theme by its id
     *
     * @param id the id of the theme
     * @return the theme
     */
    public boolean subscribe(int themeId, int userId) {
        Theme theme = themeRepository.findById(themeId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || theme == null) {
            return false;
        }
        UserThemeId id = new UserThemeId();
        id.setUserId(userId);
        id.setThemeId(themeId);

        if (userThemeRepository.existsById(id)) {
            return true;
        }

        UserTheme userTheme = new UserTheme();
        userTheme.setId(id);
        userTheme.setUser(user);
        userTheme.setTheme(theme);
        userThemeRepository.save(userTheme);
        return true;
    }

    /**
     * Unsubscribe from a theme
     *
     * @param themeId the id of the theme
     * @param userId the id of the user
     * @return true if the user was subscribed, false otherwise
     */
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

    /**
     * Check if a user is subscribed to a theme
     *
     * @param themeId the id of the theme
     * @param userId the id of the user
     * @return true if the user is subscribed, false otherwise
     */
    public boolean isSubscribed(int themeId, int userId) {
        UserThemeId id = new UserThemeId();
        id.setUserId(userId);
        id.setThemeId(themeId);
        return userThemeRepository.existsById(id);
    }

    /**
     * Get all subscribed themes for a user
     *
     * @param userId the id of the user
     * @return an array of themes
     */
    public Theme[] getSubscribedThemes(int userId) {
        Iterable<UserTheme> userThemes = userThemeRepository.findAll();
        return StreamSupport.stream(userThemes.spliterator(), true)
                .filter(userTheme -> userTheme.getUser().getId() == userId)
                .map(userTheme -> userTheme.getTheme())
                .toArray(Theme[]::new);
    }
}
