package com.rms.recruitEdge.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.rms.recruitEdge.entity.InterviewStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRequest {

    private String candidateId;

    private String interviewerId;

    private LocalDate interviewDate;

    private LocalTime interviewTime;

    private int duration;

    private String mode;

    private String meetingLink;

    private String feedback;

    private InterviewStatus status;
}
