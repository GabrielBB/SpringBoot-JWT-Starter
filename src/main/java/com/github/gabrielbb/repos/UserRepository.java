package com.github.gabrielbb.repos;

import com.github.gabrielbb.models.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByNameAndPassword(String name, String password);
}