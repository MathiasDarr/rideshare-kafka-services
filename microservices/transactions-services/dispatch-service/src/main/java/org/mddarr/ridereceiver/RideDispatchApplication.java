package org.mddarr.ridereceiver;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;

import org.mddarr.rides.event.dto.AvroRide;
import org.mddarr.rides.event.dto.AvroRideRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class RideDispatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideDispatchApplication.class, args);
	}

	public static final String INPUT_TOPIC = "input";
	public static final String OUTPUT_TOPIC = "output";
	public static final int WINDOW_SIZE_MS = 30_000;


	@Bean
	public Function<KStream<String, AvroRideRequest>, KStream<String, AvroRide>>  process_ride_requests() {
		return (rideRequestKStream -> {
			KStream<String, AvroRide> rideKStream = rideRequestKStream
					.map((key,avroRideRequest)->new KeyValue<>(key, new AvroRide("ride1",avroRideRequest.getUserId(), "Bathsheba")));
			return rideKStream;
		});

	}


}
