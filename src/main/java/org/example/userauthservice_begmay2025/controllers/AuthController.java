package org.example.userauthservice_begmay2025.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice_begmay2025.dtos.LoginRequestDTO;
import org.example.userauthservice_begmay2025.dtos.SignupRequestDto;
import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.dtos.ValidateTokenRequest;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.services.AuthService;
import org.example.userauthservice_begmay2025.utils.UserMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



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
            return UserMapperUtil.from(user);
        }catch (Exception exception){
            throw exception;
        }

    }

    @PostMapping("/login")
    public ResponseEntity<UserDto>  login (@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            Pair<User,String> userTokenPair =
                    authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
            UserDto userDto = UserMapperUtil.from(userTokenPair.a);

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.SET_COOKIE, userTokenPair.b);

            return new ResponseEntity<>(userDto,headers, HttpStatusCode.valueOf(201));
        }
        catch (Exception exception){
            throw exception;
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestBody ValidateTokenRequest validateTokenRequest){

        Boolean result = authService.validateToken(validateTokenRequest.getToken()
                ,validateTokenRequest.getUserId());

        if(result){
            return new ResponseEntity<>("Token is valid", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Token is not valid", HttpStatus.UNAUTHORIZED);
        }

    }


}
