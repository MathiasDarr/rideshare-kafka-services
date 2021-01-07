package org.mddarr.rides.request.service.controllers;


import org.mddarr.rides.request.service.models.UserRequest;
import org.mddarr.rides.request.service.services.user.UserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    UserProducer userProducer;

    @PutMapping(value = "users")
    public String postUser(@RequestBody UserRequest userRequest){

        return userProducer.postUser(userRequest);
    }


}
