package com.rms.recruitEdge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.rms.recruitEdge.entity.User;


@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;

    private UserDto userD;

}
