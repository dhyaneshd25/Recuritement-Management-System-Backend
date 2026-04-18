package com.rms.recruitEdge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.rms.recruitEdge.entity.Job;

public interface JobRepository extends MongoRepository<Job, String> {


    @Query("{ " +
           "  'createdBy': ?1, " +
           "  '$or': [ " +
           "{ 'jobTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'location': { $regex: ?0, $options: 'i' } }, " +
           "{ 'description': { $regex: ?0, $options: 'i' } } " +
           "] }")
    Page<Job> searchJobs(String search, String createdBy, Pageable pageable);


    


    @Query("{ " +
           "  'createdBy': ?1, " +
           "  '$or': [ " +
           "{ 'jobTitle': { $regex: ?0, $options: 'i' } }, " +
           "{ 'location': { $regex: ?0, $options: 'i' } }, " +
           "{ 'description': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<Job> searchJobsWithoutPagination(String search, String createdBy);
}