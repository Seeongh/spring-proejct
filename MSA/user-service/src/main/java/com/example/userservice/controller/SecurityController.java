package com.example.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user" ;
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin" ;
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager" ;
    }

    @GetMapping("/login")
    public @ResponseBody String login() {
        return "login" ;
    }

    @GetMapping("/join")
    public @ResponseBody String join(){
        return "join";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){
        return "done";
    }
}
