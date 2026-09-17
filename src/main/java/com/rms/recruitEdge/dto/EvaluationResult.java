package com.rms.recruitEdge.dto;

import java.util.List;
 
/** Full evaluation result returned to the frontend after submission. */
public record EvaluationResult(
        int overallScore,        // 0-100
        String overallFeedback,  // 2-4 sentence summary
        List<QuestionFeedback> perQuestion
) {}
 