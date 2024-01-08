package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
//import org.springframework.security.core.userdetails.UserDetailsService;

//public interface UserService extends UserDetailsService {
public interface UserService  {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll(); // 가공하지않고 db에 있는걸 그대로 가지고올거라 UserEntity list로 함 가공할거면 dto
}
