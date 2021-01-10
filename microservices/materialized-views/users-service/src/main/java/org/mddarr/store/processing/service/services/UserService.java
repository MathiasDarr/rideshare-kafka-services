package org.mddarr.store.processing.service.services;

import lombok.RequiredArgsConstructor;
import org.mddarr.store.processing.service.models.User;
import org.mddarr.store.processing.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUserById(String userid) {
        List<User> userOptional = userRepository.findAll();
        return userOptional;
    }
}
