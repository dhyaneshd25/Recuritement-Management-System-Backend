package com.rms.recruitEdge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRequest {

    private String resumeUrl;
    private String jobId;
    private String userId;
}
