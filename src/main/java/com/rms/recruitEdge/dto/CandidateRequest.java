package com.rms.recruitEdge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.rms.recruitEdge.entity.CandidateStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRequest {

    private String resumeUrl;
    private String jobId;
    private String userId;
    private CandidateStatus status;
}
