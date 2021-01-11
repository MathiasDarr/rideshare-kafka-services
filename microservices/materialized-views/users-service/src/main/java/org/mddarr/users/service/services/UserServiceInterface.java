package org.mddarr.users.service.services;

import org.mddarr.users.service.models.User;
import org.mddarr.users.service.models.requests.PostUserRequest;

import java.util.List;

public interface UserServiceInterface {

    List<User> getUserById(String userid);
    String postUser(PostUserRequest postUserRequest);


}
