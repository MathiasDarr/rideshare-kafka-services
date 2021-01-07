package org.mddarr.rides.request.service.services.user;


import org.mddarr.rides.request.service.models.DriverRequest;
import org.mddarr.rides.request.service.models.UserRequest;

public interface UserProducerInterface {
    public String postUser(UserRequest userRequest);
}
