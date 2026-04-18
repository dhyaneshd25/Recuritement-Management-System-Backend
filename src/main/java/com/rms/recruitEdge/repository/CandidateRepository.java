package com.rms.recruitEdge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.rms.recruitEdge.entity.Candidate;

public interface CandidateRepository extends MongoRepository<Candidate, String> {

    
    List<Candidate> findByJobId(String jobId);

    Page<Candidate> findByUserId(String userId, Pageable pageable);

    @Query("{ " +
           "  'userId': ?1, " +
           "  '$or': [ " +
           "{ 'userName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'jobTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'status': { $regex: ?0, $options: 'i' } } " +
           "] }")
    Page<Candidate> sfByCandidatesUserId(String search, String UserId, Pageable pageable);


    @Query("{ " +
           "  'userId': ?1, " +
           "  '$or': [ " +
           "{ 'userName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'jobTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'status': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<Candidate> sfByCandidatesUserIdWithoutPagination(String search, String UserId);
 
    @Query("{ " +
           "  'jobCreatedBy': ?1, " +
           "  '$or': [ " +
           "{ 'userName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'jobTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'status': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<Candidate> searchCandidatesWithoutPagination(String search, String jobCreatedBy);


    @Query("{ " +
           "  'jobCreatedBy': ?1, " +
           "  '$or': [ " +
           "{ 'userName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'jobTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'status': { $regex: ?0, $options: 'i' } } " +
           "] }")
    Page<Candidate> searchCandidates(String search, String jobCreatedBy, Pageable pageable);

}