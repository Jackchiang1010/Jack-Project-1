package com.example.jackstylish.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"access_token", "access_expired", "user"})
public class UserInfoDto {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_expired")
    private Integer accessExpired;

    @JsonProperty("user")
    private UserNoPwdInfo userDto;

}
