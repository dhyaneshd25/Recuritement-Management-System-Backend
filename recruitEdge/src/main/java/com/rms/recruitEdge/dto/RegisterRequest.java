package com.rms.recruitEdge.dto;

import com.rms.recruitEdge.entity.Role;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class RegisterRequest {

    private String name;

    private String email;

    private String password;

    private Role role;

//    public String getEmail(){
//     return this.email;
//    }
}