package com.rms.recruitEdge.dto;
/** Per-question feedback from Gemini's evaluation. */
public record QuestionFeedback(String questionId, int score, String feedback) {}
 
