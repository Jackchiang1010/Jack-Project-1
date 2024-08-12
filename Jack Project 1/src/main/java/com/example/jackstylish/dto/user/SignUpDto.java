package com.example.jackstylish.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

    @NonNull
    @NotBlank(message = " field cannot be empty")
    private String name;

    @Email
    private String email;

    @NonNull
    @NotBlank(message = " field cannot be empty")
    private String password;

}
