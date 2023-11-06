package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {

    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "name cannot be null")
    @Size(min = 2 , message = "name not be less than two characters")
    private String name;

    @NotNull(message = "pw cannot be null")
    @Size(min = 8, message = "pw not be less than two characters")
    private String pwd;
}
