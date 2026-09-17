package com.rms.recruitEdge.dto;
 
/** Request from frontend to generate interview questions. */
public record QuestionGenRequest(String role, String experience) {}
 