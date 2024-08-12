package com.example.jackstylish.service;

import com.example.jackstylish.dao.UserDao;
import com.example.jackstylish.dto.user.SignUpDto;
import com.example.jackstylish.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@Slf4j
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //return userId
    public void registerUser(SignUpDto signUpRequest) {
        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        userDao.createUser(signUpRequest, hashedPassword);
    }

    public void registerFbUser(SignUpDto signUpRequest) {
        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        userDao.createFbUser(signUpRequest, hashedPassword);
    }

    public UserDto getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public UserDto getUserByEmailAndPassword(String email, String password) throws NullPointerException {

        try {
            if (passwordEncoder.matches(password, userDao.getPasswordByEmail(email))) {
                return userDao.getUserByEmail(email);
            } else {
                throw new SecurityException("No user or Wrong password");
            }
        } catch (DataAccessException e) {
            throw e;
        }
    }

    @Value("${facebook.client-id}")
    private String appId;

    @Value("${facebook.client-secret}")
    private String appSecret;

    public Map<String, Object> verifyAccessToken(String fbToken) {
        String url = "https://graph.facebook.com/debug_token";

        //verity url
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("input_token", fbToken)
                .queryParam("access_token", appId + "|" + appSecret);

        // use RestTemplate send GET request
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(builder.toUriString(), Map.class);

        return response;
    }

    public boolean isAccessTokenValid(Map<String, Object> response) {
        boolean isValid = false;
        if (response.containsKey("data")) {
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            isValid = (boolean) data.get("is_valid");
        }
        return isValid;
    }

    // get user fbProfile
    public Map<String, Object> getUserProfile(String accessToken) {
        String url = "https://graph.facebook.com/me";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_token", accessToken)
                .queryParam("fields", "id,name,email,picture");

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(builder.toUriString(), Map.class);

        return response;
    }

}
