package org.example.userauthservice_begmay2025.controllers;

import org.example.userauthservice_begmay2025.dtos.LoginRequestDTO;
import org.example.userauthservice_begmay2025.dtos.SignupRequestDto;
import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.services.AuthService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


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
    public UserDto login (@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            User user =
                    authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        return from(user);
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
