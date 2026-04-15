package com.rms.recruitEdge.controller;

import java.util.List;

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

import com.rms.recruitEdge.dto.PageResponse;
import com.rms.recruitEdge.dto.UserDto;
import com.rms.recruitEdge.dto.UserRequest;
import com.rms.recruitEdge.dto.UserResponse;
import com.rms.recruitEdge.entity.Role;
import com.rms.recruitEdge.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest req){
        return ResponseEntity.status(201).body(userService.create(req));
    }

    @GetMapping("/get")
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(
        @RequestParam(defaultValue="0",name="page") int page,
        @RequestParam(defaultValue="10",name="size") int size,
        @RequestParam(defaultValue="",name="search") String search,
        @RequestParam(name="createdBy",required = true) String createdBy
    ){
        boolean fetchAll = (page == 0 && size == 10);

        Pageable pageableObj = null;

        if(fetchAll){
            pageableObj = PageRequest.of(page,size);
        }else{
            pageableObj = PageRequest.of(page-1,size);
        }

        return ResponseEntity.status(200).body(userService.getAll(pageableObj,search,fetchAll,createdBy));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name="id")String id){

            UserResponse res = userService.getById(id);
            if(res==null){
                return ResponseEntity.status(404).build();
            }

            return ResponseEntity.status(200).body(res);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable(name="id")String id,@RequestBody UserRequest req){
        if(userService.update(id, req)){
            return ResponseEntity.status(200).body("update successfully....");
        }
            return ResponseEntity.status(400).body("Enable to update....");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name="id")String id){
         if(userService.delete(id)){
            return ResponseEntity.status(204).body("delete successfully....");
        }
            return ResponseEntity.status(400).body("Enable to delete....");
  
    }

    @GetMapping("/byRole")
    public ResponseEntity<List<UserDto>> getUsersByRole(@RequestParam(name="role", required = true) Role role){
        return ResponseEntity.status(200).body(userService.getAllUserByRole(role));
    }
}

