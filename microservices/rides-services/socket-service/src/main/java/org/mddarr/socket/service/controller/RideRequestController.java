package org.mddarr.socket.service.controller;


import org.mddarr.rides.event.dto.AvroRideRequest;
import org.mddarr.socket.service.model.Greeting;
import org.mddarr.socket.service.model.HelloMessage;
import org.mddarr.socket.service.model.requests.RideRequest;

import org.mddarr.socket.service.model.responses.RideRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


@Controller
public class RideRequestController {

//    @MessageMapping("/rides/requests/alert")
//    @SendTo("/topic/rides/requests/alert")
//    public Greeting sendMessage(@Payload AvroRideRequest rideRequest) {
//        return new Greeting("dfdf");
//    }

    @MessageMapping("/rides/requests/alert")
    @SendTo("/topic/rides/requests/alert")
    public Greeting greeting(String payload) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape("Charles") + "!");
    }


}
