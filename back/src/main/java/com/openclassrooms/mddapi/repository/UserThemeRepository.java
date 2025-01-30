package com.openclassrooms.mddapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.UserTheme;
import com.openclassrooms.mddapi.model.UserThemeId;

@Repository
public interface UserThemeRepository extends CrudRepository<UserTheme, UserThemeId> {

}