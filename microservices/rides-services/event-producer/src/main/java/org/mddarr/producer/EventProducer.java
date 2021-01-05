package org.mddarr.producer;

import org.mddarr.producer.kafka.generictemplate.KafkaGenericTemplate;
import org.mddarr.producer.models.*;
import org.mddarr.producer.repositories.DriverRepository;
import org.mddarr.producer.repositories.UserRepository;

import org.mddarr.rides.event.dto.*;

import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;


public class EventProducer {

    public static void main(String[] args) throws Exception {
        populateRideCoordinates();
//        populateSingleRide();
        // populateDrivers();
//        populateSingleDriver();

    }

    public static void populateRideCoordinates() throws InterruptedException {

        Location location = new Location("Ballard", 12.0, -47.2);
        Location destination = new Location("Fremont", 12.1, 55.2);
        String rideid = "ride3";
        int npoints = 100000;

        List<RideCoordinate> rideCoordinates =  CoordinatesProducer.generateCoordinateArray(rideid, location, destination, npoints);

        KafkaGenericTemplate<AvroRideCoordinate> kafkaGenericTemplate = new KafkaGenericTemplate<>();
        KafkaTemplate<String, AvroRideCoordinate> coordinatesKafkaTemplate = kafkaGenericTemplate.getKafkaTemplate();

        coordinatesKafkaTemplate.setDefaultTopic(Constants.COORDINATES_TOPIC);

        for(RideCoordinate rideCoordinate: rideCoordinates){
            AvroRideCoordinate avroRideCoordinate = AvroRideCoordinate.newBuilder()
                    .setLatitude(rideCoordinate.getLat())
                    .setLongitude(rideCoordinate.getLng())
                    .setEventime(System.currentTimeMillis())
                    .build();
            coordinatesKafkaTemplate.sendDefault(rideid, avroRideCoordinate);
            System.out.println("SENDING COORDINATES FOR RIDE " + rideid +  " at timestamp " + avroRideCoordinate.toString() + " ");
            Thread.sleep(2000);
        }
    }

    public static void populateSingleRideRequests() throws Exception {
        KafkaGenericTemplate<AvroRideRequest> kafkaGenericTemplate = new KafkaGenericTemplate<>();
        KafkaTemplate<String, AvroRideRequest> rideRequestKafkaTemplate = kafkaGenericTemplate.getKafkaTemplate();
        rideRequestKafkaTemplate.setDefaultTopic(Constants.RIDE_REQUEST_TOPIC);
        AvroRideRequest rideRequest = new AvroRideRequest("requestid1", "user1", 3, "Ballard");
        String city = "Seattle";

        rideRequestKafkaTemplate.sendDefault(city, rideRequest);
        System.out.println("Writing ride request for '" + rideRequest.getUserId() + "' in the city of " + city);
    }

    public static void populateSingleRide() throws Exception{
        KafkaGenericTemplate<AvroRide> kafkaGenericTemplate = new KafkaGenericTemplate<AvroRide>();
        KafkaTemplate<String, AvroRide> rideRequestKafkaTemplate = kafkaGenericTemplate.getKafkaTemplate();
        rideRequestKafkaTemplate.setDefaultTopic(Constants.RIDES_TOPIC);

        AvroRide avroRide = new AvroRide("ride1","user1","driver1");
        rideRequestKafkaTemplate.sendDefault(avroRide);
        System.out.println("SENT " + avroRide);

    }

    public static void populateSingleDriver(){
        KafkaGenericTemplate<AvroDriver> kafkaGenericTemplate = new KafkaGenericTemplate<>();
        KafkaTemplate<String, AvroDriver> driverKafkaTemplate = kafkaGenericTemplate.getKafkaTemplate();
        driverKafkaTemplate.setDefaultTopic(Constants.DRIVERS_TOPIC);
        String city = "Seattle";
        AvroDriver avroDriver = AvroDriver.newBuilder()
                .setDriverid("driver1")
                .setFirstname("Erik")
                .setLastname("Charles")
                .setState(AvroDriverState.ACTIVE)
                .build();
        System.out.println("Activating driver '" + avroDriver.getFirstname() + "' to input topic " + Constants.DRIVERS_TOPIC);

        driverKafkaTemplate.sendDefault(city, avroDriver);
    }

    public static void populateAllDrivers() throws Exception{
        KafkaGenericTemplate<AvroDriver> kafkaGenericTemplate = new KafkaGenericTemplate<>();
        KafkaTemplate<String, AvroDriver> driverKafkaTemplate = kafkaGenericTemplate.getKafkaTemplate();
        driverKafkaTemplate.setDefaultTopic(Constants.DRIVERS_TOPIC);
        List<Driver> drivers = DriverRepository.getDriversFromDB();
        drivers.forEach(driver -> {
            System.out.println("Writing driver for '" + driver.getFirst_name() + "' to input topic " +
                    Constants.DRIVERS_TOPIC);
            AvroDriver avroDriver = AvroDriver.newBuilder()
                    .setDriverid(driver.getDriverid())
                    .setFirstname(driver.getFirst_name())
                    .setLastname(driver.getLast_name())
                    .setState(AvroDriverState.ACTIVE)
                    .build();
            driverKafkaTemplate.sendDefault(avroDriver);
        });
    }

}
