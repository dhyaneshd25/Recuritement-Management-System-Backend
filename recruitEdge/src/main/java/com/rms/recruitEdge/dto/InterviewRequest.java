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
public class InterviewRequest {

    private Long candidateId;

    private Long interviewerId;

    private LocalDate interviewDate;

    private LocalTime interviewTime;

    private int duration;

    private String mode;

    private String meetingLink;

    private String feedback;


}
