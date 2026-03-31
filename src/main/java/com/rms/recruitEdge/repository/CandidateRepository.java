package com.rms.recruitEdge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.rms.recruitEdge.entity.Candidate;

public interface CandidateRepository extends MongoRepository<Candidate, String> {

    
    List<Candidate> findByJobId(String jobId);

    List<Candidate> findByUserId(String userId);

 
    @Query("{ '$or': [ " +
           "{ 'userName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'jobTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'status': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<Candidate> searchCandidatesWithoutPagination(String search);


    @Query("{ '$or': [ " +
           "{ 'userName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'jobTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'status': { $regex: ?0, $options: 'i' } } " +
           "] }")
    Page<Candidate> searchCandidates(String search, Pageable pageable);
}