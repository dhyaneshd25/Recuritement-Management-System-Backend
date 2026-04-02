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
    
    private Long id;

    private Long candidateId;
    
    private String candidateName;

    private Long interviewerId;

    private String interviewerName;

    private LocalDate interviewDate;

    private LocalTime interviewTime;

    private int duration;

    private String mode;

    private String meetingLink;
    
    private String feedback;

    private String status;
}
