package com.rms.recruitEdge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rms.recruitEdge.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByJobId(Long jobId);

    List<Candidate> findByUserId(Long userId);

    
    @Query("""
        SELECT c FROM Candidate c
        WHERE
            (:search IS NULL OR

                LOWER(c.user.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                CAST(c.user.id AS string) LIKE CONCAT('%', :search, '%') OR

                LOWER(c.job.jobTitle) LIKE LOWER(CONCAT('%', :search, '%')) OR
                CAST(c.job.id AS string) LIKE CONCAT('%', :search, '%') OR

                LOWER(CAST(c.status AS string)) LIKE LOWER(CONCAT('%', :search, '%'))
            )
        """
    )
    List<Candidate> searchCandidatesWithoutPagination(String search);



    @Query("""
        SELECT c FROM Candidate c
        WHERE
            (:search IS NULL OR

                LOWER(c.user.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                CAST(c.user.id AS string) LIKE CONCAT('%', :search, '%') OR

                LOWER(c.job.jobTitle) LIKE LOWER(CONCAT('%', :search, '%')) OR
                CAST(c.job.id AS string) LIKE CONCAT('%', :search, '%') OR

                LOWER(CAST(c.status AS string)) LIKE LOWER(CONCAT('%', :search, '%'))
            )
        """
    )
    Page<Candidate> searchCandidates(String search,Pageable pageable);

}
