package com.rms.recruitEdge.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rms.recruitEdge.dto.InterviewRequest;
import com.rms.recruitEdge.dto.InterviewResponse;
import com.rms.recruitEdge.dto.PageResponse;
import com.rms.recruitEdge.entity.InterviewStatus;
import com.rms.recruitEdge.service.InterviewService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewController {
        
    private final InterviewService interviewService;

    @PostMapping("/create")
    public ResponseEntity<InterviewResponse> createInterview(@RequestBody InterviewRequest request) {
        return ResponseEntity.ok(interviewService.create(request));
    }

    @GetMapping("/get")
    public ResponseEntity<PageResponse<InterviewResponse>> getAllCandiate(
         @RequestParam(defaultValue="-1",name="page") int page,
        @RequestParam(defaultValue="10",name="size") int size,
        @RequestParam(defaultValue="",name="search") String search
    ) {
        boolean fetchAll = (page == 0 && size == 10);

        Pageable pageableObj = null;

        if(fetchAll){
            pageableObj = PageRequest.of(page,size);
        }else{
            pageableObj = PageRequest.of(page-1,size);
        }

        return ResponseEntity.status(200).body(interviewService.getAll(pageableObj,search,fetchAll));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<InterviewResponse> getInterviewById(@PathVariable(name="id",required=true)String id){

        InterviewResponse res = interviewService.getById(id);
        
        if(res==null){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(res);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateInterview(@PathVariable(name="id")String id,@RequestBody InterviewRequest req){
        if(interviewService.update(id, req)){
            return ResponseEntity.status(400).body("update successfully....");
        }
            return ResponseEntity.status(400).body("Enable to update....");
    }

    @PatchMapping("/update/{id}/status")
    public ResponseEntity<InterviewResponse> updateStatus(
            @PathVariable String id,
            @RequestParam InterviewStatus
             status) {

        return ResponseEntity.ok(interviewService.updateStatus(id, status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCandiate(@PathVariable(name="id")String id){
        boolean check = interviewService.delete(id);

        if(check){
            return ResponseEntity.status(204).body("delete successfully....");
        }
            return ResponseEntity.status(400).body("Enable to delete....");
  
    }

}
