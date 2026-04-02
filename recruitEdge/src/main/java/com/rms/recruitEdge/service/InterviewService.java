package com.rms.recruitEdge.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rms.recruitEdge.dto.InterviewRequest;
import com.rms.recruitEdge.dto.InterviewResponse;
import com.rms.recruitEdge.dto.PageResponse;
import com.rms.recruitEdge.entity.Interview;
import com.rms.recruitEdge.entity.InterviewStatus;
import com.rms.recruitEdge.entity.Candidate;
import com.rms.recruitEdge.entity.User;
import com.rms.recruitEdge.repository.CandidateRepository;
import com.rms.recruitEdge.repository.InterviewRepository;
import com.rms.recruitEdge.repository.UserRepository;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public InterviewResponse create(InterviewRequest request) {
    
        Interview interview = toInterviewEntity(request);

        Interview saveInterview = interviewRepository.save(interview);

        return toInterviewResponse(saveInterview);
    }

    public PageResponse<InterviewResponse> getAll(Pageable pageable,String search,boolean fetchAll){

        List<Interview> interviews;

        Page<Interview> pages = null;

        if(fetchAll){
            interviews = interviewRepository.searchInterviewWithoutPagination(search);
        }else{
            pages = interviewRepository.searchInterview(search, pageable);
            interviews = pages.getContent();
        }

        PageResponse<InterviewResponse> res = new PageResponse<>();

        res.setData(interviews.stream().map(this::toInterviewResponse).toList());

        if (!fetchAll) {

            res.setCurrentpage(pages.getNumber()+1);

            res.setTotalElements(pages.getTotalElements());

            res.setTotalPages(pages.getTotalPages());

        } else {

            res.setCurrentpage(1);

            res.setTotalElements(interviews.size());

            res.setTotalPages(1);
        }

        return res;

    }

    public InterviewResponse getById(Long id){

        Interview interview = interviewRepository.findById(id).orElse(null);

        if(interview==null){
            return null;
        }

        return toInterviewResponse(interview);
    }

    public boolean update(Long id,InterviewRequest req){

        Interview  interview = interviewRepository.findById(id).orElse(null);

        if(interview==null){
            return false;
        }

        if(req.getCandidateId()!=null){

            Candidate candidate = candidateRepository.findById(req.getCandidateId())
            .orElseThrow(() -> new RuntimeException("Candidate not found"));

            interview.setCandidate(candidate);

        }
        if(req.getInterviewerId()!=null){

            User user  = userRepository.findById(req.getInterviewerId())
                .orElseThrow(() -> new RuntimeException("Interviewer not found"));

            interview.setInterviewer(user);
        }

        if(req.getInterviewDate()!=null){
            interview.setInterviewDate(req.getInterviewDate());
        }

        if(req.getInterviewTime()!=null){
            interview.setInterviewTime(req.getInterviewTime());
        }

        if(req.getDuration()>0){
            interview.setDuration(req.getDuration());
        }

        if(req.getMeetingLink()!=null){
            interview.setMeetingLink(req.getMeetingLink());
        }
        
        if(req.getMode()!=null){
            req.setMode(req.getMode());
        }

        if(req.getFeedback()!=null){
            req.setFeedback(req.getFeedback());
        }

        interviewRepository.save(interview);

        return true;
    }

    public InterviewResponse updateStatus(Long id, InterviewStatus status) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interview.setStatus(status);

        Interview saveInterview = interviewRepository.save(interview);

        return toInterviewResponse(saveInterview);
    }

    public boolean delete(Long id){

        Interview interview = interviewRepository.findById(id).orElse(null);

        if(interview==null){
            return false;
        }

        interviewRepository.delete(interview);

        return true;
    }

    public Interview toInterviewEntity(InterviewRequest request){

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        User user  = userRepository.findById(request.getInterviewerId())
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        Interview interview = new Interview();

        interview.setStatus(InterviewStatus.SCHEDULED);
        interview.setCandidate(candidate);
        interview.setInterviewer(user);
        interview.setInterviewDate(request.getInterviewDate());
        interview.setInterviewTime(request.getInterviewTime());
        interview.setDuration(request.getDuration());
        interview.setMeetingLink(request.getMeetingLink());
        interview.setMode(request.getMode());
        interview.setFeedback(request.getFeedback());


        return interview;

    }

    public InterviewResponse toInterviewResponse(Interview interview) {

        InterviewResponse res = new InterviewResponse();

        res.setId(interview.getId());
        res.setCandidateId(interview.getCandidate().getId());
        res.setCandidateName(interview.getCandidate().getUser().getName());
        res.setInterviewerId(interview.getInterviewer().getId());
        res.setInterviewerName(interview.getInterviewer().getName());
        res.setInterviewDate(interview.getInterviewDate());
        res.setInterviewTime(interview.getInterviewTime());
        res.setDuration(interview.getDuration());
        res.setMode(interview.getMode());
        res.setMeetingLink(interview.getMeetingLink());
        res.setFeedback(interview.getFeedback());
        res.setStatus(interview.getStatus().name());

        return res;
    }

}
