package com.example.jackstylish.controller;

import com.example.jackstylish.dto.user.*;
import com.example.jackstylish.service.UserService;
import com.example.jackstylish.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    //localhost:8080/user/signup
    @RequestMapping(value = "/api/{apiVersion}/user/signup", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@PathVariable(value = "apiVersion") String apiVersion,
                                          @Validated @RequestBody SignUpDto signUpRequest) {

        try {
            if (Objects.equals(apiVersion, "1.0")) {

                String email = signUpRequest.getEmail();
                UserDto userDtoExist = userService.getUserByEmail(email);

                ShowUserDataDto showSignUpDataDto = new ShowUserDataDto();

                if (userDtoExist == null) {
                    userService.registerUser(signUpRequest);

                    userDtoExist = userService.getUserByEmail(email);

                    showSignUpDataDto = getUser(userDtoExist);

                    return new ResponseEntity<>(showSignUpDataDto, HttpStatus.OK);

                } else {
                    return new ResponseEntity<>("Email Already Exists", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("apiVersion is wrong", HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>("missing data", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //localhost:8080/user/signin
    @RequestMapping(value = "/api/{apiVersion}/user/signin", method = RequestMethod.POST)
    public ResponseEntity<?> userLogin(@PathVariable(value = "apiVersion") String apiVersion,
                                       @Validated @RequestBody Map<String, Object> requestBody) {

        try {
            if (Objects.equals(apiVersion, "1.0")) {

                String provider = (String) requestBody.get("provider");

                ShowUserDataDto showSignInDataDto = new ShowUserDataDto();

                if (provider.equals("native")) {

                    SignInDto signInDto = new SignInDto();

                    String email = requestBody.get("email").toString();
                    String password = requestBody.get("password").toString();

                    signInDto.setEmail(email);
                    signInDto.setPassword(password);

                    UserDto userDtoExist = userService.getUserByEmailAndPassword(email, password);

                    showSignInDataDto = getUser(userDtoExist);

                    return new ResponseEntity<>(showSignInDataDto, HttpStatus.OK);

                }
                //fb unregister
                else {

                    String fbToken = requestBody.get("access_token").toString();

                    SignInFbDto signInFbDto = new SignInFbDto();

                    signInFbDto.setAccessToken(fbToken);

                    // 驗證 Facebook Access Token
                    Map<String, Object> tokenResponse = userService.verifyAccessToken(fbToken);

                    log.info(tokenResponse.toString());

                    if (userService.isAccessTokenValid(tokenResponse)) {
                        log.info("tokenResponse : {}", tokenResponse);

                        Map<String, Object> userProfile = userService.getUserProfile(fbToken);

                        SignUpDto signUpRequest = new SignUpDto();
                        signUpRequest.setEmail((String) userProfile.get("email"));
                        signUpRequest.setName((String) userProfile.get("name"));
                        signUpRequest.setPassword("TEST");

                        UserDto userDtoExist = userService.getUserByEmail(signUpRequest.getEmail());

                        if (userDtoExist == null) {
                            userService.registerFbUser(signUpRequest);

                            userDtoExist = userService.getUserByEmail(signUpRequest.getEmail());

                            ShowUserDataDto showSignUpDataDto = getUser(userDtoExist);

                            return new ResponseEntity<>(showSignUpDataDto, HttpStatus.OK);
                        } else {
                            showSignInDataDto = getUser(userDtoExist);

                            return new ResponseEntity<>(showSignInDataDto, HttpStatus.OK);
                        }

                    } else {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(Collections.singletonMap("error", "Invalid access token"));
                    }

                }

            } else {
                return new ResponseEntity<>("apiVersion is wrong", HttpStatus.OK);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("provider is null", HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("missing data", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    private ShowUserDataDto getUser(UserDto userDtoExist) {

        String jwtToken = jwtUtils.generateToken(userDtoExist);
        Integer jwtExpired = jwtUtils.EXPIRATION_TIME;

        UserInfoDto sendUserDto = new UserInfoDto();
        UserNoPwdInfo userNoPwdInfo = new UserNoPwdInfo();
        ShowUserDataDto showSignUpDataDto = new ShowUserDataDto();

        userNoPwdInfo.setId(userDtoExist.getId());
        userNoPwdInfo.setProvider(userDtoExist.getProvider());
        userNoPwdInfo.setName(userDtoExist.getName());
        userNoPwdInfo.setEmail(userDtoExist.getEmail());
        userNoPwdInfo.setPicture(userDtoExist.getPicture());

        sendUserDto.setAccessToken(jwtToken);
        sendUserDto.setAccessExpired(jwtExpired);
        sendUserDto.setUserDto(userNoPwdInfo);

        showSignUpDataDto.setData(sendUserDto);

        return showSignUpDataDto;
    }

    @RequestMapping(value = "/api/{apiVersion}/user/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getUserProfile(@PathVariable(value = "apiVersion") String apiVersion,
                                            @RequestHeader("Authorization") String token) {

        try {

            if (Objects.equals(apiVersion, "1.0")) {

                if (token == null || !token.startsWith("Bearer ")) {
                    return new ResponseEntity<>("No token", HttpStatus.UNAUTHORIZED);
                }

                String jwtToken = token.substring(7); // Remove "Bearer " prefix

                String email = jwtUtils.extractUsername(jwtToken);
                UserDto userDtoExist = userService.getUserByEmail(email);

                ShowUserProfileDto showUserProfileDto = new ShowUserProfileDto();
                UserProfileDto userProfileDto = new UserProfileDto();

                userProfileDto.setProvider(userDtoExist.getProvider());
                userProfileDto.setName(userDtoExist.getName());
                userProfileDto.setEmail(userDtoExist.getEmail());
                userProfileDto.setPicture(userDtoExist.getPicture());

                showUserProfileDto.setData(userProfileDto);

                return new ResponseEntity<>(showUserProfileDto, HttpStatus.OK);

            } else {
                return new ResponseEntity<>("apiVersion is wrong", HttpStatus.OK);
            }

        } catch (DataAccessException e) {
            return new ResponseEntity<>("missing data", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

}
