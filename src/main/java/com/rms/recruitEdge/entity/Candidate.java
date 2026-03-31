package com.rms.recruitEdge.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "candidates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {

    @Id
    private String id;

    private String resumeUrl;

    private CandidateStatus status;

    
    @Indexed
    private String jobId;
    private String jobTitle;

    
    @Indexed
    private String userId;
    private String userName;

    private LocalDateTime createdAt;
}