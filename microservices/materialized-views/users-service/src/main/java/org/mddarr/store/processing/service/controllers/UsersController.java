package org.mddarr.store.processing.service.controllers;


import lombok.RequiredArgsConstructor;
import org.mddarr.store.processing.service.models.User;
import org.mddarr.store.processing.service.models.requests.PostUserRequest;
import org.mddarr.store.processing.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping(value="/users/{userid}")
    public List<User> getUserDetail(String userid){
        return userService.getUserById(userid);
    }

    @PutMapping(value = "/users")
    public String postUser(@RequestBody PostUserRequest postUserRequest){
        return userService.postUser(postUserRequest);
    }

}
