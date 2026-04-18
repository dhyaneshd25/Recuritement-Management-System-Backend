package com.rms.recruitEdge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rms.recruitEdge.dto.JobRequest;
import com.rms.recruitEdge.dto.JobResponse;
import com.rms.recruitEdge.dto.PageResponse;
import com.rms.recruitEdge.entity.Job;
import com.rms.recruitEdge.entity.JobType;
import com.rms.recruitEdge.repository.JobRepository;

@Service
public class JobService {
    
    @Autowired
    private JobRepository jobRepository;

    public JobResponse create(JobRequest req){
       
        Job newJob = toEntity(req);

        Job savedJob = jobRepository.save(newJob);

        JobResponse res = toJobResponse(savedJob);

        return res;
    }


    public PageResponse<JobResponse> getAll(Pageable pageable,String search,boolean fetchAll,String createdBy){

        List<Job> jobs;

        Page<Job> pages = null;

        if(fetchAll){
            jobs = jobRepository.searchJobsWithoutPagination(search,createdBy);
        }else{
            pages = jobRepository.searchJobs(search, createdBy, pageable);
            jobs = pages.getContent();
        }

        // Page<Job> pages = jobRepository.findAll(pageable);

        // Page<Job> pages = jobRepository.searchJobs(search, pageable);

        PageResponse<JobResponse> res = new PageResponse<>();

        res.setData(jobs.stream().map(this::toJobResponse).toList());

        if (!fetchAll) {

            res.setCurrentpage(pages.getNumber()+1);

            res.setTotalElements(pages.getTotalElements());

            res.setTotalPages(pages.getTotalPages());

        } else {

            res.setCurrentpage(1);

            res.setTotalElements(jobs.size());

            res.setTotalPages(1);
        }

        return res;

    }

    public PageResponse<JobResponse>  getA(){

        List<Job> jobs = jobRepository.findAll();

        PageResponse<JobResponse> res = new PageResponse<>();

        res.setData(jobs.stream().map(this::toJobResponse).toList());

        res.setCurrentpage(1);

        res.setTotalElements(jobs.size());

        res.setTotalPages(1);

        return res;
    }
    
    public JobResponse getById(String id){

        Job job  = jobRepository.findById(id).orElse(null);

        if(job==null){
            return null;
        }

        return toJobResponse(job);
    }

    public boolean update(String id,JobRequest req){

        Job job = jobRepository.findById(id).orElse(null);

        if(job==null){
            return false;
        }

        if(req.getJobTitle()!=null){
            job.setJobTitle(req.getJobTitle());
        }
        if(req.getDescription()!=null){
            job.setDescription(req.getDescription());
        }
        if(req.getLocation()!=null){
            job.setLocation(req.getLocation());
        }
        if(req.getJobType()!=null){
            job.setJobType(req.getJobType());
        }
        if(req.getExperience()!=null){
            job.setExperience(req.getExperience());
        }
        if(req.getSalaryRange()!=null){
            job.setSalaryRange(req.getSalaryRange());
        }
        if(req.getStatus()!=null){
            job.setStatus(req.getStatus());
        }
        if(req.getCreatedBy()!=null){
            job.setCreatedBy(req.getCreatedBy());
        }

        jobRepository.save(job);

        return true;
    }

    public boolean delete(String id){

        Job job = jobRepository.findById(id).orElse(null);

        if(job==null){
            return false;
        }

        jobRepository.delete(job);

        return true;
    }

    public List<JobType> getJobTypes(){
        return List.of(JobType.values());
    }

    // Mapper
    
    public Job toEntity(JobRequest req){

        Job newJob = new Job();

        newJob.setDescription(req.getDescription());
        newJob.setExperience(req.getExperience());
        newJob.setCompanyName(req.getCompanyName());
        newJob.setCreatedBy(req.getCreatedBy());
        newJob.setJobTitle(req.getJobTitle());
        newJob.setLocation(req.getLocation());
        newJob.setJobType(req.getJobType());
        newJob.setStatus(req.getStatus());
        newJob.setColor(req.getColor());
        newJob.setSalaryRange(req.getSalaryRange());

        return newJob;
    }

    public JobResponse toJobResponse(Job req){

        JobResponse job = new JobResponse();
        job.setId(req.getId());
        job.setDescription(req.getDescription());
        job.setExperience(req.getExperience());
        job.setCompanyName(req.getCompanyName());
        job.setCreatedBy(req.getCreatedBy());
        job.setJobTitle(req.getJobTitle());
        job.setJobType(req.getJobType());
        job.setLocation(req.getLocation());
        job.setStatus(req.getStatus());
        job.setColor(req.getColor());
        job.setSalaryRange(req.getSalaryRange());

        return job;
    }


}
