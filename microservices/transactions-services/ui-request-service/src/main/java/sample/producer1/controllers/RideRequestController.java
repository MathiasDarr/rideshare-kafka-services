package sample.producer1.controllers;


import org.mddarr.orders.event.dto.AvroOrder;
import org.mddarr.orders.event.dto.FirstOrder;
import org.mddarr.orders.event.dto.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sample.producer1.models.RideRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
public class RideRequestController {

    @Autowired
    BlockingQueue<RideRequest> unbounded = new LinkedBlockingQueue<>();

    @PutMapping(value = "/orders")
    public String sendMessage(@RequestBody RideRequest rideRequest) {
        unbounded.offer(rideRequest);
        return "ride request has been posted.. ";
    }

}
