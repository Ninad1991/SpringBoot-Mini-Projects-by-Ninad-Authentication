package org.example.userauthservice_begmay2025.controllers;

import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.models.User;
import org.example.userauthservice_begmay2025.repos.UserRepo;
import org.example.userauthservice_begmay2025.services.UserService;
import org.example.userauthservice_begmay2025.utils.UserMapperUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;
    private final UserService userService;

    public UserController(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUserbyId(@PathVariable Long id) {
        User user = userService.getUserbyId(id);
        if (user == null) {
            throw new  RuntimeException("User with Id:"+ id + " not found");
        }

        return UserMapperUtil.from(user);
    }

    @GetMapping("/{email}")
    public UserDto getUserbyEmail(@PathVariable String email) {
        User user = userService.getUserbyEmail(email);
        if (user == null) {
            throw new  RuntimeException("User with Email:"+ email + " not found");
        }

        return UserMapperUtil.from(user);

    }

    @PostMapping
    public UserDto createUser(@RequestBody User input) {
        User user = userService.createUser(input);
        return UserMapperUtil.from(user);

    }

    @PatchMapping("userId/{userId}/email/{newEmail}")
    public UserDto updateUserbyEmail(@PathVariable Long userId,
                                     @PathVariable String newEmail) {
        User user = userService.updateUserbyEmail(userId, newEmail);

        if (user == null) {
            throw new  RuntimeException("User with Id:"+ userId + " not found");
        }

        return UserMapperUtil.from(user);
    }

    @DeleteMapping
    public Boolean deleteUser(@RequestParam String email) {
        Boolean result = userService.deleteUser(email);
        if (!result) {
            throw new  RuntimeException("User with Email:"+ email + " not found");
        }
        return result;

    }

}
