package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import com.example.userservice.vo.greeting;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

     private Environment env;
     private UserService userService;

   @Autowired //bin 주입
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

//    @Autowired
//    private greeting greeting;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service yml from config and greeting like :  %s",
                env.getProperty("greeting.message"));

    }

//    @GetMapping("/welcome")
//    public String welcome() {
//        //return  env.getProperty("greeting.message");
//       // return greeting.getMessage();
//    }

   @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user,UserDto.class);
       userService.createUser(userDto);

       return "Create user method is called";
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll(); //반복자 ㅜㅜ 저게뭐여

        List<ResponseUser> result = new ArrayList<>(); // responseuser를 타입으로 가지는 list 선언
        userList.forEach(v-> {  // userEntity list를 끝날때까지 i처럼 v라는 매개가 userentity하나를 들고있음
            result.add(new ModelMapper().map(v, ResponseUser.class)); //그 userentity하나를 responseUser하나랑 매핑해줘
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) { //pathvariable이라는 어노테이션으로 userid가 변수명이달라도 오게해줌
        UserDto user = userService.getUserByUserId(userId);
        ResponseUser returnValue = new ModelMapper().map(user, ResponseUser.class); //그 userentity하나를 responseUser하나랑 매핑해줘


        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
