package com.openclassrooms.mddapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USER_THEMES")
@Data
public class UserTheme {

    @PrimaryKeyJoinColumns({
        @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id"),
        @PrimaryKeyJoinColumn(name = "theme_id", referencedColumnName = "id")
    })

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;
}
