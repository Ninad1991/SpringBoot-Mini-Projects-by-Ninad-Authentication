package org.example.userauthservice_begmay2025.services;

import org.example.userauthservice_begmay2025.exceptions.PasswordMismatchException;
import org.example.userauthservice_begmay2025.exceptions.UserAlreadySignedInException;
import org.example.userauthservice_begmay2025.exceptions.UserNotFoundInSystemException;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.repos.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepo userRepo;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User signup(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);

        if (userOptional.isPresent()) {
            throw new UserAlreadySignedInException("user already exists, please login using email: "+email);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userRepo.save(user);

        return user;

    }

    public User login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundInSystemException("Please register first");
        }

        String pwd = userOptional.get().getPassword();

        if (!password.equals(pwd)) {
            throw new
                    PasswordMismatchException("You have entered incorrect password, please use correct password or reset it");
        }
        return userOptional.get();
    }


}
