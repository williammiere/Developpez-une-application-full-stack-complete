package com.openclassrooms.mddapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  User findByEmail(String email);

}
