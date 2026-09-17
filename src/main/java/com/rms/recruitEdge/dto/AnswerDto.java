package com.rms.recruitEdge.dto;
 
/** One answer the user submits for a given question. */
public record AnswerDto(String questionId, String question, String answer) {}