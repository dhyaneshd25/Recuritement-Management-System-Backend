package com.rms.recruitEdge.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rms.recruitEdge.dto.JobRequest;
import com.rms.recruitEdge.dto.JobResponse;
import com.rms.recruitEdge.dto.PageResponse;
import com.rms.recruitEdge.service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {
    
    @Autowired
    private JobService jobService;

    @PostMapping("/create")
    public ResponseEntity<JobResponse> createJob(@RequestBody JobRequest req){
        return ResponseEntity.status(201).body(jobService.create(req));
    }

    @GetMapping("/get")
    public ResponseEntity<PageResponse<JobResponse>> getAllJobs(
        @RequestParam(defaultValue="0",name="page") int page,
        @RequestParam(defaultValue="10",name="size") int size,
        @RequestParam(defaultValue="",name="search") String search
    ){
        boolean fetchAll = (page == 0 && size == 10);

        Pageable pageableObj = null;

        if(fetchAll){
            pageableObj = PageRequest.of(page,size);
        }else{
            pageableObj = PageRequest.of(page-1,size);
        }

        return ResponseEntity.status(200).body(jobService.getAll(pageableObj,search,fetchAll));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable(name="id")Long id){

            JobResponse res = jobService.getById(id);
            if(res==null){
                return ResponseEntity.status(404).build();
            }

            return ResponseEntity.status(200).body(res);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJob(@PathVariable(name="id")Long id,@RequestBody JobRequest req){
        if(jobService.update(id, req)){
            return ResponseEntity.status(400).body("update successfully....");
        }
            return ResponseEntity.status(400).body("Enable to update....");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable(name="id")Long id){
         if(jobService.delete(id)){
            return ResponseEntity.status(204).body("delete successfully....");
        }
            return ResponseEntity.status(400).body("Enable to delete....");
  
    }
}
