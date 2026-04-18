package com.rms.recruitEdge.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "interviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interview {

    @Id
    private String id;

    
    @Indexed
    private String candidateId;
    private String candidateName;

    private String candidateUserId;
   
    @Indexed
    private String interviewerId;
    private String interviewerName;

    private String jobTitle;

    private LocalDate interviewDate;
    private LocalTime interviewTime;

    private int duration;
    private String mode;

    private String meetingLink;

    private String feedback;

    private InterviewStatus status;

    @Indexed
    private String candidateCreatedBy;
}