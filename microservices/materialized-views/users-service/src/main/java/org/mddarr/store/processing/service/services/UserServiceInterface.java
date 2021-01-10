package org.mddarr.store.processing.service.services;

import org.mddarr.store.processing.service.models.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> getUserById(String userid);


}
