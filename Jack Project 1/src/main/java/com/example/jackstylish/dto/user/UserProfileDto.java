package com.example.jackstylish.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDto {

    private String provider;
    private String name;
    private String email;
    private String picture;

}
