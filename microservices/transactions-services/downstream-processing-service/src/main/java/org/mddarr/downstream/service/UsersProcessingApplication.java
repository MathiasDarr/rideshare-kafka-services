package org.mddarr.downstream.service;

import org.apache.kafka.streams.kstream.*;


import org.mddarr.rides.event.dto.AvroUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.DefaultAfterRollbackProcessor;
import org.springframework.util.backoff.FixedBackOff;


import java.util.UUID;
import java.util.function.Function;

@SpringBootApplication
public class UsersProcessingApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UsersProcessingApplication.class, args);
	}

	@Bean
	public Function<KStream<String, AvroUserEvent>, KStream<String,AvroUserEvent>> process_users_requests() {
		return avroUserEventKStream -> {
			avroUserEventKStream.foreach((k,avroUserEvent)->{
				System.out.println(avroUserEvent);
			});
			return avroUserEventKStream;
		};
	}

	@Bean
	public ListenerContainerCustomizer<AbstractMessageListenerContainer<byte[], byte[]>> customizer() {
		// Disable retry in the AfterRollbackProcessor
		return (container, destination, group) -> container.setAfterRollbackProcessor(
				new DefaultAfterRollbackProcessor<byte[], byte[]>(
						(record, exception) -> System.out.println("Discarding failed record: " + record),
						new FixedBackOff(0L, 0)));
	}




//	@Bean
//	public Function<KStream<String, AvroRideRequest>, KStream<String, AvroRide>>  process_ride_requests() {
//		return (rideRequestKStream -> {
//			KStream<String, AvroRide> rideKStream = rideRequestKStream
//					.map((key,avroRideRequest)->new KeyValue<>(key, new AvroRide("ride1",avroRideRequest.getUserId(), "Bathsheba")));
//			return rideKStream;
//		});
//
//	}


}
