package org.example.userauthservice_begmay2025.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice_begmay2025.exceptions.PasswordMismatchException;
import org.example.userauthservice_begmay2025.exceptions.UserAlreadySignedInException;
import org.example.userauthservice_begmay2025.exceptions.UserNotFoundInSystemException;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.models.UserSession;
import org.example.userauthservice_begmay2025.repos.UserRepo;
import org.example.userauthservice_begmay2025.repos.UserSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserSessionRepo userSessionRepo;

    @Autowired
    private SecretKey secretKey;

//    public AuthService(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }

    public User signup(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);

        if (userOptional.isPresent()) {
            throw new UserAlreadySignedInException("user already exists, please login using email: "+email);
        }

        User user = new User();
        user.setEmail(email);
        //user.setPassword(password);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepo.save(user);

        return user;

    }

    public Pair<User, String> login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundInSystemException("Please register first");
        }

        String pwd = userOptional.get().getPassword();


   //     if (!password.equals(pwd)) {
        if (!bCryptPasswordEncoder.matches(password, pwd)) {
            throw new
                    PasswordMismatchException("You have entered incorrect password, please use correct password or reset it");
        }


        // token generation logic

//        String message = "{\n" +
//                " \"email\": \""+email+"\",\n" +
//                " \"roles\": [\n" +
//                " \"instructor\", \n" +
//                " \"buddy\"\n" +
//                " ],\n" +
//                " \"expirationDate\": \"22Sep2025\"\n" +
//                "}";

  //      byte[] content = message.getBytes(StandardCharsets.UTF_8);

        Map<String,Object> claims = new HashMap<>();
        claims.put("user_id", userOptional.get().getId());
        Long nowInMillis = System.currentTimeMillis();
        claims.put("iat",nowInMillis); //issued at
        claims.put("exp",nowInMillis + 10000000);
        claims.put("iss","scaler_uas");

//        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        SecretKey secretKey = algorithm.key().build();

        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

        // persisting the geneerated token

        UserSession userSession = new UserSession();
        userSession.setUser(userOptional.get());
        userSession.setToken(token);

        userSessionRepo.save(userSession);

        return new Pair<User,String>(userOptional.get(),token);
    }

    public Boolean validateToken(String token, Long userId) {
        Optional<UserSession> optionalUserSession = userSessionRepo.findByTokenAndUserId(token,userId);

        if(optionalUserSession.isEmpty()){
            return false;
        }

        UserSession userSession = optionalUserSession.get();
        String persistedToken = userSession.getToken();

        //parsing token to get payload to get expiry

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();

        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long expiry = (Long) claims.get("exp");
        Long currentTime = System.currentTimeMillis();

        if(currentTime > expiry){
            System.out.println("Token has expired");
            return false;
        }
        return true;
    }



}
