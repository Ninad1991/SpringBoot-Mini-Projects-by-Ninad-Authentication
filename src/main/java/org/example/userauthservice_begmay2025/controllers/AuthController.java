package org.example.userauthservice_begmay2025.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice_begmay2025.dtos.LoginRequestDTO;
import org.example.userauthservice_begmay2025.dtos.SignupRequestDto;
import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }


    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto){
        try{
            User user =
                    authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword());
        }catch (Exception exception){
            throw exception;
        }

        return null;

    }

    @PostMapping("/login")
    public ResponseEntity<UserDto>  login (@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            Pair<User,String> userTokenPair =
                    authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
            UserDto userDto = from(userTokenPair.a);

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", "Bearer " + userTokenPair.b);

        return new ResponseEntity<>(userDto, headers, HttpStatusCode.valueOf(201));
        }
        catch (Exception exception){
            throw exception;
        }
    }

    private UserDto from (User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
