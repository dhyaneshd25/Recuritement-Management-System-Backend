package com.rms.recruitEdge.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rms.recruitEdge.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

}