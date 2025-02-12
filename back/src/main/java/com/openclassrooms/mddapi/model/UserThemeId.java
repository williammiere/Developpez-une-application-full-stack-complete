package com.openclassrooms.mddapi.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * The entity class for the user theme id
 */
@Embeddable
@Data
public class UserThemeId implements Serializable {

    @Column(name="user_id")
    private int userId;

    @Column(name="theme_id")
    private int themeId;
}