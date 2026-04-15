package com.rms.recruitEdge.dto;

import com.rms.recruitEdge.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private String id;  

    private String name;

    private String email;

    private String password;

    private Role role; 
}
