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
public class GoogleLoginRequest {
 
   private String token;

   private Role role;
}
