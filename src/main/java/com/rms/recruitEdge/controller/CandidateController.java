package com.rms.recruitEdge.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.rms.recruitEdge.dto.CandidateRequest;
import com.rms.recruitEdge.dto.CandidateResponse;
import com.rms.recruitEdge.dto.PageResponse;
import com.rms.recruitEdge.entity.CandidateStatus;
import com.rms.recruitEdge.service.CandidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateController {

    @Autowired
    private final CandidateService candidateService;

    @PostMapping("/create")
    public ResponseEntity<CandidateResponse> createCandidate(@RequestBody CandidateRequest request) {
        return ResponseEntity.ok(candidateService.create(request));
    }

    @GetMapping("/get")
    public ResponseEntity<PageResponse<CandidateResponse>> getAllCandiate(
        @RequestParam(defaultValue="-1",name="page") int page,
        @RequestParam(defaultValue="10",name="size") int size,
        @RequestParam(defaultValue="",name="search") String search,
        @RequestParam(name="jobCreatedBy", required = true) String jobCreatedBy
    ) {
        boolean fetchAll = (page == 0 && size == 10);

        Pageable pageableObj = null;

        if(fetchAll){
            pageableObj = PageRequest.of(page,size);
        }else{
            pageableObj = PageRequest.of(page-1,size);
        }

        return ResponseEntity.status(200).body(candidateService.getAll(pageableObj,search,fetchAll,jobCreatedBy));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable(name="id",required=true)String id){

        CandidateResponse res = candidateService.getById(id);
        
        if(res==null){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(res);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCandidate(@PathVariable(name="id")String id,@RequestBody CandidateRequest req){
        if(candidateService.update(id, req)){
            return ResponseEntity.status(200).body("update successfully....");
        }
            return ResponseEntity.status(400).body("Enable to update....");
    }

    @PatchMapping("/update/{id}/status")
    public ResponseEntity<CandidateResponse> updateStatus(
            @PathVariable String id,
            @RequestParam CandidateStatus
             status) {

        return ResponseEntity.ok(candidateService.updateStatus(id, status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCandiate(@PathVariable(name="id")String id){
        boolean check = candidateService.delete(id);

        if(check){
            return ResponseEntity.status(204).body("delete successfully....");
        }
            return ResponseEntity.status(400).body("Enable to delete....");
  
    }

}
