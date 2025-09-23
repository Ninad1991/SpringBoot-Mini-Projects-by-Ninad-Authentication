package org.example.userauthservice_begmay2025.services;

import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.models.State;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public User getUserbyId(Long id) {
        Optional<User> user = userRepo.findByIdEquals(id);
        return user.orElse(null);

    }


    public User getUserbyEmail(String email) {
        Optional<User> user = userRepo.findByEmailEquals(email);
        return user.orElse(null);

    }


    public User createUser(User input) {
        User newUser = new User();
        newUser.setEmail(input.getEmail());
//        newUser.setPassword(input.getPassword());
        newUser.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));
        return userRepo.save(newUser);
    }


    public User updateUserbyEmail(Long userId, String newEmail) {
        Optional<User> userOptional = userRepo.findByIdEquals(userId);

        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        user.setEmail(newEmail);
        return userRepo.save(user);

    }


    // We are handling both soft delete and hard delete both in the same API, it can be done using two different APIs
    // you can check with domain expert or the interviewer if soft delete or hard delete is required

    public Boolean deleteUser(String email) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);
        if (userOptional.isEmpty()) {
            return false;
        } else {
            User user = userOptional.get();
            if (user.getState().equals(State.ACTIVE)) {
                user.setState(State.INACTIVE);
                user.setLastUpdatedAt(new Date());
                userRepo.save(user);
                return true;
            } else {
                userRepo.deleteById(user.getId());
                return true;
            }
        }

    }

}
