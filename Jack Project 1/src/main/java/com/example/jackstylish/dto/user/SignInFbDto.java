package com.example.jackstylish.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SignInFbDto {

    @NonNull
    @NotBlank
    private String provider;


    @JsonProperty("access_token")
    private String accessToken;

}
