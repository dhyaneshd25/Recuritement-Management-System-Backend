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

    @Query("{ " +
           "  'createdBy': ?1, " +
           "  '$or': [ " +
           "{ 'UserTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'location': { $regex: ?0, $options: 'i' } }, " +
           "{ 'description': { $regex: ?0, $options: 'i' } } " +
           "] }")
    Page<User> searchUsers(String search, String createdBy, Pageable pageable);



    @Query("{ " +
           "  'createdBy': ?1, " +
           "  '$or': [ " +
           "{ 'UserTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'location': { $regex: ?0, $options: 'i' } }, " +
           "{ 'description': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<User> searchUsersWithoutPagination(String search, String createdBy);
}