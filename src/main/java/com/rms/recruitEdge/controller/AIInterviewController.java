package com.rms.recruitEdge.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rms.recruitEdge.dto.EvaluationResult;
import com.rms.recruitEdge.dto.QuestionDto;
import com.rms.recruitEdge.dto.QuestionGenRequest;
import com.rms.recruitEdge.dto.SubmitAnswersRequest;
import com.rms.recruitEdge.service.AIInterviewService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/aiinterview")
@RequiredArgsConstructor
public class AIInterviewController {

    private final AIInterviewService interviewService;

    // POST /api/interview/questions   body: { "role": "...", "experience": "..." }
    @PostMapping("/questions")
    public List<QuestionDto> getQuestions(@RequestBody QuestionGenRequest request) {
        return interviewService.generateQuestions(request.role(), request.experience());
    }

    // POST /api/interview/submit   body: { "role", "experience", "answers": [...] }
    @PostMapping("/submit")
    public EvaluationResult submitAnswers(@RequestBody SubmitAnswersRequest request) {
        return interviewService.evaluateAnswers(
                request.role(), request.experience(), request.answers());
    }
}