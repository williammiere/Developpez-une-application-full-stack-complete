package com.openclassrooms.mddapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {
    Iterable<Article> findByThemeIn(Theme[] themes);
}
