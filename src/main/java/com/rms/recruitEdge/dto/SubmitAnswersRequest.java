package com.rms.recruitEdge.dto;
 
import java.util.List;
 
/** Full submission payload from the frontend after all questions are answered. */
public record SubmitAnswersRequest(String role, String experience, List<AnswerDto> answers) {}
 
