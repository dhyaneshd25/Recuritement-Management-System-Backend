package com.rms.recruitEdge.dto;

import java.util.List;
 
/**
 * What Gemini returns for question generation.
 * Spring AI's structured output (ChatClient .entity(...)) binds the
 * model's JSON response directly onto this record - no manual parsing.
 */
public record QuestionGenResponse(List<QuestionDto> questions) {}
 