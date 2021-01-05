package org.mddarr.rides.request.service.controllers;


import org.mddarr.rides.request.service.models.DriverRequest;
import org.mddarr.rides.request.service.models.RideRequest;
import org.mddarr.rides.request.service.services.driver.AvroDriverProducer;
import org.mddarr.rides.request.service.services.riderequest.AvroRideRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverRequestController {

    @Autowired
    AvroDriverProducer avroDriverProducer;

    @PutMapping("drivers/activate")
    @CrossOrigin(origins = "http://localhost:8090")
    public String activateDriverRequest(@RequestBody DriverRequest driverRequest){
        return avroDriverProducer.activateDriver(driverRequest);
    }

    @PutMapping("drivers/deactivate")
    @CrossOrigin(origins = "http://localhost:8090")
    public String deactivateDriverRequest(@RequestBody DriverRequest driverRequest){
        return avroDriverProducer.deactivateDriver(driverRequest);
    }


}
