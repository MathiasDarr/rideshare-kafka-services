package org.mddarr.processingservice;

import org.apache.kafka.streams.kstream.KStream;
import org.mddarr.processingservice.models.UserEntity;
import org.mddarr.processingservice.repositories.UserRepository;

import org.mddarr.rides.event.dto.AvroUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.DefaultAfterRollbackProcessor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.backoff.FixedBackOff;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

@SpringBootApplication
@EnableTransactionManagement
public class ProcessingServiceApplication {

	@Autowired
	UserRepository userRepository;

	private AtomicBoolean shouldFail= new AtomicBoolean(false);

	public static void main(String[] args) {
		SpringApplication.run(ProcessingServiceApplication.class, args);
	}


	@Bean
	public Function<KStream<String, AvroUserEvent>, KStream<String, AvroUserEvent>> process_users_requests() {
		return (userKStream -> userKStream);

	}

	@Transactional
	@Bean
	public Function<AvroUserEvent, AvroUserEvent> process() {
		return avroUser -> {
			System.out.println("Received event = " +  avroUser);
			UserEntity user = new UserEntity();
			user.setFirst_name(avroUser.getFirstname());

			if (shouldFail.get()) {
				shouldFail.set(false);
				throw new RuntimeException("Simulated network error");
			} else {
				//We fail every other request as a test
				shouldFail.set(true);
			}
			System.out.println("Saving person=" +  user);

			UserEntity savedPerson = userRepository.save(user);

			UserEntity event = new UserEntity();
			event.setFirst_name(savedPerson.getFirst_name());
//			event.setType("PersonSaved");
//			logger.info("Sent event={}", event);
			return avroUser;
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

}
