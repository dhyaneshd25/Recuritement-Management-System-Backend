package com.rms.recruitEdge.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    private String id; 

    @Indexed
    private String jobTitle;

    private String description;

    private String companyName;    

    private String location;

    private String experience;

    private JobType jobType;

    private String salaryRange;

    private JobStatus status;

    private String createdBy; 

    private String color;

}