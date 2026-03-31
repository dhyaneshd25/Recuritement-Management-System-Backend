package com.rms.recruitEdge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.rms.recruitEdge.entity.Interview;

public interface InterviewRepository extends MongoRepository<Interview, String> {

  
    @Query("{ '$or': [ " +
           "{ 'candidateName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'candidateId': { $regex: ?0, $options: 'i' } }, " +

           "{ 'interviewerName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'interviewerId': { $regex: ?0, $options: 'i' } }, " +

           "{ 'mode': { $regex: ?0, $options: 'i' } }, " +
           "{ 'status': { $regex: ?0, $options: 'i' } }, " +

           "{ 'interviewDate': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<Interview> searchInterviewWithoutPagination(String search);


    
    @Query("{ '$or': [ " +
           "{ 'candidateName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'candidateId': { $regex: ?0, $options: 'i' } }, " +

           "{ 'interviewerName': { $regex: ?0, $options: 'i' } }, " +
           "{ 'interviewerId': { $regex: ?0, $options: 'i' } }, " +

           "{ 'mode': { $regex: ?0, $options: 'i' } }, " +
           "{ 'status': { $regex: ?0, $options: 'i' } }, " +

           "{ 'interviewDate': { $regex: ?0, $options: 'i' } } " +
           "] }")
    Page<Interview> searchInterview(String search, Pageable pageable);
}