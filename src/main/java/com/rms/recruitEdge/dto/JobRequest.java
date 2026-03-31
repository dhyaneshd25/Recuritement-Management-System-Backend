package com.rms.recruitEdge.dto;

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

    private int experience;

    private String salaryRange;

    private String status;

    private String createdBy;

    private String location;
}