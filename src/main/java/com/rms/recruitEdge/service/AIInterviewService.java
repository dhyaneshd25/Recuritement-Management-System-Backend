package com.rms.recruitEdge.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.rms.recruitEdge.dto.EvaluationResult;
import com.rms.recruitEdge.dto.AnswerDto;
import com.rms.recruitEdge.dto.QuestionDto;
import com.rms.recruitEdge.dto.QuestionGenResponse;

@Service
public class AIInterviewService {

    private final ChatClient chatClient;

    public AIInterviewService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Generates 5 role/experience-appropriate interview questions via Gemini.
     * Uses Spring AI's structured output (.entity(...)) so the JSON response
     * is bound straight onto QuestionGenResponse - no manual parsing.
     */
    public List<QuestionDto> generateQuestions(String role, String experience) {

        String template = """
                You are an experienced technical interviewer.
                Generate exactly 10 interview questions for a candidate applying
                for the role of "{role}" with "{experience}" years of experience.

                Rules:
                - Mix of conceptual and practical/scenario-based questions,
                  calibrated to the stated experience level.
                - Each question must be answerable verbally in under 2 minutes.
                - Do not include answers, hints, or explanations - questions only.
                - Do not number the questions in the text itself.
                """;

                try{
                        QuestionGenResponse response = chatClient.prompt()
                                .user(u -> u.text(template).params(Map.of("role", role, "experience", experience)))
                                .call()
                                .entity(QuestionGenResponse.class);

                                // Assign stable IDs the frontend will echo back on submission
                                return response.questions().stream()
                                        .map(q -> new QuestionDto(UUID.randomUUID().toString(), q.text()))
                                        .collect(Collectors.toList());
                }catch(Exception e){
                        e.printStackTrace();
                }

               
                System.out.print("jdfkljdskf");

                return null;

    }

    /**
     * Sends all question/answer pairs to Gemini in a single call for scoring
     * and feedback, calibrated to the stated role and experience level.
     */
    public EvaluationResult evaluateAnswers(String role, String experience, List<AnswerDto> answers) {

        String qaBlock = answers.stream()
                .map(a -> "Question ID: " + a.questionId()
                        + "\nQuestion: " + a.question()
                        + "\nCandidate's answer: " + (a.answer() == null || a.answer().isBlank()
                                ? "(no answer given / left blank)"
                                : a.answer()))
                .collect(Collectors.joining("\n\n"));

        String template = """
                You are an experienced technical interviewer evaluating a candidate
                for the role of "{role}" with "{experience}" years of experience.

                Below are interview questions and the candidate's spoken/written answers.
                Evaluate each answer on correctness, clarity, and depth appropriate for
                the stated experience level.

                For each question, return the SAME "Question ID" you were given, a score
                from 0-100, and 1-3 sentences of specific, constructive feedback.
                Also return an overall score (0-100, can be a weighted average) and a
                2-4 sentence overall summary covering strengths and the most important
                area to improve.

                A blank/no answer should score low but still receive constructive
                feedback on what a strong answer would have covered.

                {qaBlock}
                """;

        return chatClient.prompt()
                .user(u -> u.text(template).params(Map.of(
                        "role", role,
                        "experience", experience,
                        "qaBlock", qaBlock)))
                .call()
                .entity(EvaluationResult.class);
    }
}