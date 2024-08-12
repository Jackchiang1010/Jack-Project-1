package com.example.jackstylish.dto.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "provider", "name", "email", "picture"})
public class UserNoPwdInfo {

    private Integer id;
    private String provider;
    private String name;
    private String email;
    private String picture;

}
