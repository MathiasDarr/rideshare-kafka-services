package org.mddarr.producer.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Driver {
    String driverid;
    String first_name;
    String last_name;
    Integer average_shift_length;
    String city;

    public Driver(){}


    public String getDriverid() {
        return driverid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Integer getAverage_shift_length() {
        return average_shift_length;
    }

    public void setAverage_shift_length(Integer average_shift_length) {
        this.average_shift_length = average_shift_length;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
