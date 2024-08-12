package com.example.jackstylish.rowmapper;

import com.example.jackstylish.dto.user.UserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserDto> {

    @Override
    public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDto user = new UserDto();
        user.setId(rs.getInt("id"));
        user.setProvider(rs.getString("provider"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPicture(rs.getString("picture"));
        return user;
    }

}
