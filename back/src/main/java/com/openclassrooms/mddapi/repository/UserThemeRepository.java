package com.openclassrooms.mddapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.UserTheme;

@Repository
public interface UserThemeRepository extends CrudRepository<UserTheme, Integer> {

}