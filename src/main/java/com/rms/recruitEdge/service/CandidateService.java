package com.rms.recruitEdge.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rms.recruitEdge.dto.CandidateRequest;
import com.rms.recruitEdge.dto.CandidateResponse;
import com.rms.recruitEdge.dto.PageResponse;
import com.rms.recruitEdge.entity.Candidate;
import com.rms.recruitEdge.entity.CandidateStatus;
import com.rms.recruitEdge.entity.Job;
import com.rms.recruitEdge.entity.User;
import com.rms.recruitEdge.repository.CandidateRepository;
import com.rms.recruitEdge.repository.JobRepository;
import com.rms.recruitEdge.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public CandidateResponse create(CandidateRequest request) {
    
        Candidate candidate = toCandidateEntity(request);

        Candidate saveCandidate = candidateRepository.save(candidate);

        return toCandidateResponse(saveCandidate);
    }

    public PageResponse<CandidateResponse> getAll(Pageable pageable,String search,boolean fetchAll, String jobCreatedBy){

        List<Candidate> candidates;

        Page<Candidate> pages = null;

        if(fetchAll){
            candidates = candidateRepository.searchCandidatesWithoutPagination(search, jobCreatedBy);
        }else{
            pages = candidateRepository.searchCandidates(search, jobCreatedBy, pageable);
            candidates = pages.getContent();
        }

        // Page<Job> pages = jobRepository.findAll(pageable);

        // Page<Job> pages = jobRepository.searchJobs(search, pageable);

        PageResponse<CandidateResponse> res = new PageResponse<>();

        res.setData(candidates.stream().map(this::toCandidateResponse).toList());

        if (!fetchAll) {

            res.setCurrentpage(pages.getNumber()+1);

            res.setTotalElements(pages.getTotalElements());

            res.setTotalPages(pages.getTotalPages());

        } else {

            res.setCurrentpage(1);

            res.setTotalElements(candidates.size());

            res.setTotalPages(1);
        }

        return res;

    }

    public CandidateResponse getById(String id){

        Candidate candidate = candidateRepository.findById(id).orElse(null);

        if(candidate==null){
            return null;
        }

        return toCandidateResponse(candidate);
    }

    public boolean update(String id,CandidateRequest req){

        Candidate  candidate = candidateRepository.findById(id).orElse(null);

        if(candidate==null){
            return false;
        }

        if(req.getResumeUrl()!=null){
            candidate.setResumeUrl(req.getResumeUrl());
        }
        if(req.getJobId()!=null){

            Job job = jobRepository.findById(req.getJobId())
            .orElseThrow(() -> new RuntimeException("Job not found"));

            candidate.setJobId(job.getId());
            candidate.setJobTitle(job.getJobTitle());
            candidate.setJobCreatedBy(job.getCreatedBy());

        }
        if(req.getUserId()!=null){

            User user  = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

            candidate.setUserId(user.getId());
            candidate.setUserName(user.getName());
        }
        if(req.getStatus()!=null){
            candidate.setStatus(req.getStatus());
        }
        

        candidateRepository.save(candidate);

        return true;
    }

    public CandidateResponse updateStatus(String id, CandidateStatus status) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        candidate.setStatus(status);

        Candidate saveCandidate = candidateRepository.save(candidate);

        return toCandidateResponse(saveCandidate);
    }

    public boolean delete(String id){

        Candidate candidate = candidateRepository.findById(id).orElse(null);

        if(candidate==null){
            return false;
        }

        candidateRepository.delete(candidate);

        return true;
    }

    public Candidate toCandidateEntity(CandidateRequest request){

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User user  = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Candidate candidate = new Candidate();
        candidate.setResumeUrl(request.getResumeUrl());
        candidate.setStatus(CandidateStatus.APPLIED);
        candidate.setJobId(job.getId());
        candidate.setJobTitle(job.getJobTitle());
        candidate.setCreatedAt(LocalDateTime.now());
        candidate.setUserId(user.getId());
        candidate.setUserName(user.getName());
        candidate.setJobCreatedBy(job.getCreatedBy());

        return candidate;

    }

    public CandidateResponse toCandidateResponse(Candidate candidate) {

        CandidateResponse res = new CandidateResponse();

        res.setId(candidate.getId());
        res.setResumeUrl(candidate.getResumeUrl());
        res.setStatus(candidate.getStatus().name());

        res.setJobId(candidate.getJobId());
        res.setJobTitle(candidate.getJobTitle());

        res.setUserId(candidate.getUserId());
        res.setName(candidate.getUserName());
        res.setCreatedAt(candidate.getCreatedAt());
        res.setJobCreatedBy(candidate.getJobCreatedBy());

        return res;
    }
}
