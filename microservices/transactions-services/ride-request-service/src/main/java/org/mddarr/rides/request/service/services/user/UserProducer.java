package org.mddarr.rides.request.service.services.user;

import org.mddarr.rides.event.dto.AvroDriver;
import org.mddarr.rides.event.dto.AvroDriverState;
import org.mddarr.rides.event.dto.AvroUserEvent;
import org.mddarr.rides.event.dto.AvroUserEventType;
import org.mddarr.rides.request.service.Constants;
import org.mddarr.rides.request.service.models.DriverRequest;
import org.mddarr.rides.request.service.models.UserRequest;
import org.mddarr.rides.request.service.services.riderequest.AvroRideRequestProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer implements UserProducerInterface {

    @Autowired
    private KafkaTemplate<String, AvroUserEvent> kafkaUserTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AvroRideRequestProducer.class);

    @Override
    public String postUser(UserRequest userRequest) {
        System.out.println("THE DRIVER ACTIVATION LOOKS LIKE " + userRequest);
        AvroUserEvent user = AvroUserEvent.newBuilder()
                .setUserid(null)
                .setCity(userRequest.getCity())
                .setPhonenumber(userRequest.getPhonenumber())
                .setEmail(userRequest.getEmail())
                .setFirstname(userRequest.getFirstname())
                .setLastname(userRequest.getLastname())
                .setType(AvroUserEventType.REGISTRAIION_PENDING)
                .build();
        kafkaUserTemplate.setDefaultTopic(Constants.USERS_TOPIC);
        kafkaUserTemplate.sendDefault(user);
        return "registation_is_pending";
    }


}
