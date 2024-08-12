package com.example.jackstylish.dao;

import com.example.jackstylish.dto.user.SignUpDto;
import com.example.jackstylish.dto.user.UserDto;
import com.example.jackstylish.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Integer createUser(SignUpDto signUpRequest, String hashedPassword) {

        String sql = "INSERT INTO user(provider, name, email, password, picture) VALUES ('native', :name,:email,:password, '');";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", signUpRequest.getName());
        map.put("email", signUpRequest.getEmail());
        map.put("password", hashedPassword);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
    }

    public UserDto getUserByEmail(String userEmail) {

        String sql = "SELECT * From user WHERE email = :userEmail";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userEmail", userEmail);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, map, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String getPasswordByEmail(String userEmail) {
        String sql = "SELECT password From user WHERE email = :userEmail";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userEmail", userEmail);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, map, (ResultSet rs, int rowNum) -> rs.getString("password"));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer createFbUser(SignUpDto signUpRequest, String hashedPassword) {
        String sql = "INSERT INTO user(provider, name, email, password, picture) VALUES ('facebook', :name,:email,:password, '');";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", signUpRequest.getName());
        map.put("email", signUpRequest.getEmail());
        map.put("password", hashedPassword);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
    }

}
