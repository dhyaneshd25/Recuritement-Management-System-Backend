package com.rms.recruitEdge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rms.recruitEdge.entity.Interview;


public interface InterviewRepository extends JpaRepository<Interview, Long>{
    

    @Query("""
        SELECT i FROM Interview i
        WHERE
            (:search IS NULL OR

                LOWER(i.candidate.user.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                CAST(i.candidate.id AS string) LIKE CONCAT('%', :search, '%') OR

                LOWER(i.interviewer.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                CAST(i.interviewer.id AS string) LIKE CONCAT('%', :search, '%') OR

                LOWER(i.mode) LIKE LOWER(CONCAT('%', :search, '%')) OR

                LOWER(CAST(i.status AS string)) LIKE LOWER(CONCAT('%', :search, '%')) OR

                CAST(i.interviewDate AS string) LIKE CONCAT('%', :search, '%')
            )
    """)
    List<Interview> searchInterviewWithoutPagination(String search);


    @Query("""
        SELECT i FROM Interview i
        WHERE
            (:search IS NULL OR

                LOWER(i.candidate.user.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                CAST(i.candidate.id AS string) LIKE CONCAT('%', :search, '%') OR

                LOWER(i.interviewer.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                CAST(i.interviewer.id AS string) LIKE CONCAT('%', :search, '%') OR

                LOWER(i.mode) LIKE LOWER(CONCAT('%', :search, '%')) OR

                LOWER(CAST(i.status AS string)) LIKE LOWER(CONCAT('%', :search, '%')) OR

                CAST(i.interviewDate AS string) LIKE CONCAT('%', :search, '%')
            )
    """)
    Page<Interview> searchInterview(String search, Pageable pageable);
}
