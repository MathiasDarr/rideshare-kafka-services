package org.mddarr.rides.request.service.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DriverRequest {

    private String driverid;
    private String driver_first_name;
    private String driver_last_name;
    private String city;
    private int seats;

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driver_id) {
        this.driverid = driver_id;
    }

    public String getDriver_first_name() {
        return driver_first_name;
    }

    public void setDriver_first_name(String driver_first_name) {
        this.driver_first_name = driver_first_name;
    }

    public String getDriver_last_name() {
        return driver_last_name;
    }

    public void setDriver_last_name(String driver_last_name) {
        this.driver_last_name = driver_last_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
