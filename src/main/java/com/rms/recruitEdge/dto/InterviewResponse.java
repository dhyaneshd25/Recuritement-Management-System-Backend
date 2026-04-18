package com.rms.recruitEdge.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponse {
    
    private String id;

    private String candidateId;
    
    private String candidateName;

    private String candidateUserId;

    private String jobTitle;

    private String interviewerId;

    private String interviewerName;

    private LocalDate interviewDate;

    private LocalTime interviewTime;

    private int duration;

    private String mode;

    private String meetingLink;
    
    private String feedback;

    private String status;

    private String candidateCreatedBy;
}
