package com.rms.recruitEdge.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.rms.recruitEdge.entity.Role;
import com.rms.recruitEdge.entity.User;


public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    @Query("""
      {
  "$or": [
    { "name": { "$regex": ?0, "$options": "i" } },
    { "email": { "$regex": ?0, "$options": "i" } },
     { "role": { "$regex": ?0, "$options": "i" } }
  ]
}
       """)
    Page<User> searchUsers(String search, Pageable pageable);



    @Query("""
      {
  "$or": [
    { "name": { "$regex": ?0, "$options": "i" } },
    { "email": { "$regex": ?0, "$options": "i" } },
     { "role": { "$regex": ?0, "$options": "i" } }
  ]
}
       """)
    List<User> searchUsersWithoutPagination(String search);
}