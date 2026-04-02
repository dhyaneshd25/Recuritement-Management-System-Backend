package com.rms.recruitEdge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rms.recruitEdge.entity.Job;

public interface JobRepository extends JpaRepository<Job,Long>{
    
    @Query("""
        SELECT j FROM Job j
        WHERE (:search IS NULL OR :search = '' OR
              LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :search, '%')) OR
              LOWER(j.location) LIKE LOWER(CONCAT('%', :search, '%')) OR
              LOWER(j.description) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    Page<Job> searchJobs(String search, Pageable pageable);
    
    @Query("""
        SELECT j FROM Job j
        WHERE (:search IS NULL OR :search = '' OR
                LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :search, '%')) OR
                LOWER(j.location) LIKE LOWER(CONCAT('%', :search, '%')) OR
                LOWER(j.description) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    List<Job> searchJobsWithoutPagination(String search);
    
}
