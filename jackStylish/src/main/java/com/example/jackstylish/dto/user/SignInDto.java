package com.example.jackstylish.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {

    @NonNull
    @NotBlank
    private String provider;

    @Email
    private String email;

    @NonNull
    @NotBlank
    private String password;

    @JsonProperty("access_token")
    private String accessToken;

}
