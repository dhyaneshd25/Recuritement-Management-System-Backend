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


    public PageResponse<JobResponse> getAll(Pageable pageable,String search,boolean fetchAll){

        List<Job> jobs;

        Page<Job> pages = null;

        if(fetchAll){
            jobs = jobRepository.searchJobsWithoutPagination(search);
        }else{
            pages = jobRepository.searchJobs(search, pageable);
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

    public JobResponse getById(Long id){

        Job job  = jobRepository.findById(id).orElse(null);

        if(job==null){
            return null;
        }

        return toJobResponse(job);
    }

    public boolean update(Long id,JobRequest req){

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
        if(req.getExperience()>0){
            job.setExperience(req.getExperience());
        }
        if(req.getSalaryRange()!=null){
            job.setSalaryRange(req.getSalaryRange());
        }
        if(req.getStatus()!=null){
            job.setStatus(req.getStatus());
        }
        if(req.getCreateBy()!=null){
            job.setCreatedBy(req.getCreateBy());
        }

        jobRepository.save(job);

        return true;
    }

    public boolean delete(Long id){

        Job job = jobRepository.findById(id).orElse(null);

        if(job==null){
            return false;
        }

        jobRepository.delete(job);

        return true;
    }


    // Mapper
    
    public Job toEntity(JobRequest req){

        Job newJob = new Job();

        newJob.setDescription(req.getDescription());
        newJob.setExperience(req.getExperience());
        newJob.setCreatedBy(req.getCreateBy());
        newJob.setJobTitle(req.getJobTitle());
        newJob.setLocation(req.getLocation());
        newJob.setStatus(req.getStatus());
        newJob.setSalaryRange(req.getSalaryRange());

        return newJob;
    }

    public JobResponse toJobResponse(Job req){

        JobResponse job = new JobResponse();
        job.setId(req.getId());
        job.setDescription(req.getDescription());
        job.setExperience(req.getExperience());
        job.setCreatedBy(req.getCreatedBy());
        job.setJobTitle(req.getJobTitle());
        job.setLocation(req.getLocation());
        job.setStatus(req.getStatus());
        job.setSalaryRange(req.getSalaryRange());

        return job;
    }
}
