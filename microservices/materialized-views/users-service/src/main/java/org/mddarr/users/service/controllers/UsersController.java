package org.mddarr.users.service.controllers;


import org.mddarr.users.service.models.User;
import org.mddarr.users.service.models.requests.PostUserRequest;
import org.mddarr.users.service.services.UserService;
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
