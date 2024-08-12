package com.example.jackstylish.dto.user;

import lombok.Data;

@Data
public class UserDto {

    private Integer id;
    private String provider;
    private String name;
    private String email;
    private String password;
    private String picture;

}
