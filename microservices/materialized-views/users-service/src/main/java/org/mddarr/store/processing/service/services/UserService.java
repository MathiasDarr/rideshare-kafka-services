package org.mddarr.store.processing.service.services;

import lombok.RequiredArgsConstructor;
import org.mddarr.store.processing.service.models.User;
import org.mddarr.store.processing.service.models.requests.PostUserRequest;
import org.mddarr.store.processing.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUserById(String userid) {
        List<User> userOptional = userRepository.findAll();
        return userOptional;
    }

    @Override
    public String postUser(PostUserRequest postUserRequest) {
        Date date = new Date(System.currentTimeMillis());
        System.out.println("THE CURRENT DATE IS " + date.toString());
        User user = new User();
        user.setEmail(postUserRequest.getEmail());
        user.setFirst_name(postUserRequest.getFirst_name());
        user.setLast_name(postUserRequest.getLast_name());
        user.setPassword(postUserRequest.getPhone_number());
        user.setUserid(UUID.randomUUID().toString());
        user.setUpdate_ts(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        return user.getUserid();
    }
}
