package com.rms.recruitEdge.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponse {

    private String id;


    private String resumeUrl;

    private String status;

    private String jobId;
    private String jobTitle;

    private String userId;
    private String name;

    private String jobCreatedBy;

    private LocalDateTime createdAt;
}