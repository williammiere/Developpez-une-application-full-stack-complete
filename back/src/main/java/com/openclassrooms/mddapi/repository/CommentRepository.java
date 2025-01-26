package com.openclassrooms.mddapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

}
