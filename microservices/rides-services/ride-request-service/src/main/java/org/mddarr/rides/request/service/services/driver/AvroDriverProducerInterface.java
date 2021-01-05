package org.mddarr.rides.request.service.services.driver;


import org.mddarr.rides.request.service.models.DriverRequest;
import org.mddarr.rides.request.service.models.RideRequest;

public interface AvroDriverProducerInterface {
    public String activateDriver(DriverRequest driverRequest);
    public String deactivateDriver(DriverRequest driverDeactivateRequest);
}
