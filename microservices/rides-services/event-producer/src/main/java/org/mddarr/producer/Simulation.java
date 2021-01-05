/*
The methods in this file simulate running of the system.

 */

package org.mddarr.producer;

import org.mddarr.producer.kafka.generictemplate.KafkaGenericTemplate;
import org.mddarr.producer.models.Driver;
import org.mddarr.producer.models.DrivingSession;
import org.mddarr.producer.models.RideRequestSession;
import org.mddarr.producer.models.User;
import org.mddarr.producer.repositories.DriverRepository;
import org.mddarr.producer.repositories.DriverSessionRepository;
import org.mddarr.producer.repositories.UserRepository;

import org.mddarr.rides.event.dto.AvroDriver;
import org.mddarr.rides.event.dto.AvroRide;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;

public class Simulation {

    public static void simulate_user_ride_requests() throws InterruptedException {

        int iteration = 0;

        List<User> users = UserRepository.getUsers();
        System.out.println("Users " + users.size());

        Random random_object = new Random();
        Set<User> available_users = new HashSet<>(users);

        while(true) {
            List<User> requesting_users = new ArrayList<>();
            // Select  the drivers from the inactive pool to activate sessions
            for (User user : available_users) {
                double probablity = random_object.nextDouble();
                if (probablity < .005) {
                    requesting_users.add(user);
                }
            }
            Iterator<User> availableUserIterator = users.iterator();

            while (availableUserIterator.hasNext()) {
                User user = availableUserIterator.next();
                double ride_request_probability = random_object.nextDouble();

                if (ride_request_probability < .008) {
                    RideRequestSession rideRequestSession = new RideRequestSession(user, iteration);
                    String requestid = rideRequestSession.getRequestid();
                    available_users.remove(user);
                    requesting_users.add(user);
                }
            }
            iteration += 1;
            Thread.sleep(200);
        }
    }


    public static void simulate_drivers_logging_in() throws InterruptedException {
        KafkaGenericTemplate<AvroDriver> kafkaGenericTemplate = new KafkaGenericTemplate<AvroDriver>();
        KafkaTemplate<String, AvroDriver> driverRequestKafkaTemplate = kafkaGenericTemplate.getKafkaTemplate();
        driverRequestKafkaTemplate.setDefaultTopic(Constants.DRIVERS_TOPIC);

        List<Driver> drivers = DriverRepository.getDriversFromDB();
        System.out.println(drivers);

        Random rand = new Random();

        Set<Driver> active_drivers = new HashSet<>();
        Set<DrivingSession> active_sessions = new HashSet<>();
        Set<Driver> inactive_drivers = new HashSet<>(drivers);

        int iteration = 0;

        while(true){

            List<Driver> activating_drivers = new ArrayList<>();
            // Select  the drivers from the inactive pool to activate sessions
            for(Driver driver: inactive_drivers){
                double probablity = rand.nextDouble();
                if(probablity < .02){
                    activating_drivers.add(driver);
                }
            }
//
            Iterator<DrivingSession> drivingSessionIterator = active_sessions.iterator();

            while(drivingSessionIterator.hasNext()){
                DrivingSession session = drivingSessionIterator.next();
                if(session.verifySessionEnding(iteration)){
                    Driver driver = session.getDriver();
                    inactive_drivers.add(driver);
                    DriverSessionRepository.insertSession( session.getSessionid() ,driver.getDriverid(), session.getSession_length(), session.getSession_start(), iteration);
                    System.out.println("SESSION WITH DRIVER " + driver.getFirst_name() + " " + driver.getLast_name() + " HAS ENDED AT LENGTH " + session.getSession_length());
                    drivingSessionIterator.remove();
                    active_drivers.remove(driver);
                }else{
                    session.increment_session_length();
                }
            }

            for(Driver driver: activating_drivers){
                inactive_drivers.remove(driver);
                active_drivers.add(driver);
//                String session_id = DriverSessionRepository.insertDrivingSession(driver.getDriverid(), driver.getAverage_shift_length());
                DrivingSession drivingSession = new DrivingSession(driver, UUID.randomUUID().toString(), iteration);
                active_sessions.add(drivingSession);
            }
            iteration += 1;
            Thread.sleep(100);
        }
    }




}
