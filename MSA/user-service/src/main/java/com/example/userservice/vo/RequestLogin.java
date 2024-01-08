package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {

    @NotNull(message = "email cannot be null")
    @Size(min =2, message = "email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "PASSWORD CANNOT BE NULL")
    @Size(min = 2, message= "password must be larger then 2")
    private String password;

}
