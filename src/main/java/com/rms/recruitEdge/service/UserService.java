package com.rms.recruitEdge.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rms.recruitEdge.dto.UserRequest;
import com.rms.recruitEdge.dto.UserResponse;
import com.rms.recruitEdge.dto.PageResponse;
import com.rms.recruitEdge.dto.UserDto;
import com.rms.recruitEdge.entity.Role;
import com.rms.recruitEdge.entity.User;
import com.rms.recruitEdge.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest req){
       
        User newUser = toEntity(req);

        User savedUser = userRepository.save(newUser);

        UserResponse res = toUserResponse(savedUser);

        return res;
    }

    public PageResponse<UserResponse> getAll(Pageable pageable,String search,boolean fetchAll){

        List<User> users;

        Page<User> pages = null;

        if(fetchAll){
            users = userRepository.searchUsersWithoutPagination(search);
        }else{
            pages = userRepository.searchUsers(search, pageable);
            users = pages.getContent();
        }

        // Page<User> pages = userRepository.findAll(pageable);

        // Page<User> pages = userRepository.searchUsers(search, pageable);

        PageResponse<UserResponse> res = new PageResponse<>();

        res.setData(users.stream().map(this::toUserResponse).toList());

        if (!fetchAll) {

            res.setCurrentpage(pages.getNumber()+1);

            res.setTotalElements(pages.getTotalElements());

            res.setTotalPages(pages.getTotalPages());

        } else {

            res.setCurrentpage(1);

            res.setTotalElements(users.size());

            res.setTotalPages(1);
        }

        return res;

    }

    public UserResponse getById(String id){

        User user  = userRepository.findById(id).orElse(null);

        if(user==null){
            return null;
        }

        return toUserResponse(user);
    }

    public boolean update(String id,UserRequest req){

        User user = userRepository.findById(id).orElse(null);

        if(user==null){
            return false;
        }

        if(req.getName()!=null){
            user.setName(req.getName());
        }
        if(req.getEmail()!=null){
            user.setEmail(req.getEmail());
        }       
        if(req.getRole()!=null){
            user.setRole(req.getRole());
        }
        if(req.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }        
        userRepository.save(user);

        return true;
    }

    public boolean delete(String id){

        User user = userRepository.findById(id).orElse(null);

        if(user==null){
            return false;
        }

        userRepository.delete(user);

        return true;
    }

    public List<UserDto> getAllUserByRole(Role role){
        return userRepository.findByRole(role).stream().map(u -> toUserToDto(u)).toList();
    }

    public Map<Role,Integer> roleUserCount(){

        List<User> users = userRepository.findAll();
        Map<Role,Integer> mp = new HashMap<>();

        for(Role role : Role.values()){
            mp.put(role,0);
        }

        for(User u : users){
            mp.put(u.getRole(),mp.getOrDefault(u.getRole(), 1)+1);
        }
        return mp;
    }
    // Mapper
    
    public User toEntity(UserRequest req){

        User newUser = new User();
        
        newUser.setName(req.getName());
        newUser.setEmail(req.getEmail());
        newUser.setRole(req.getRole());
        newUser.setPassword(req.getPassword());

        return newUser;
    }

    public UserResponse toUserResponse(User req){

        UserResponse user = new UserResponse();

        user.setId(req.getId());
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setPassword(req.getPassword());

        return user;
    }

    public UserDto toUserToDto(User req){

        UserDto user = new UserDto();

        user.setId(req.getId());
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole().name());

        return user;
    }
    
}
