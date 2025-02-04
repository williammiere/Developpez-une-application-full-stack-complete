package com.openclassrooms.mddapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Theme;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Integer> {
    Theme findByTitle(String title);
}