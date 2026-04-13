package com.rms.recruitEdge.dto;

import com.rms.recruitEdge.entity.JobStatus;
import com.rms.recruitEdge.entity.JobType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest{

    private String jobTitle;

    private String description;

    private String companyName;

    private String experience;

    private String salaryRange;

    private JobType jobType;

    private String createdBy;

    private String location;

    private String color;

    private JobStatus status;
}