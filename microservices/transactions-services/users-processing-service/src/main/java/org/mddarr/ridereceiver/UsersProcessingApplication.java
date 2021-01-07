package org.mddarr.ridereceiver;

import org.apache.kafka.streams.kstream.*;

import org.mddarr.ridereceiver.models.UserEntity;
import org.mddarr.ridereceiver.repositories.UserRepository;
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
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

@SpringBootApplication
@EnableTransactionManagement
public class UsersProcessingApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UsersProcessingApplication.class, args);
	}

	@Bean
	@Transactional
	public Function<KStream<String, AvroUserEvent>, KStream<String, AvroUserEvent>>  process_ride_requests() {
		return (userStream) -> {
//			System.out.println(userStrea);
			userStream.foreach((x,avroUser)->{
				System.out.println(avroUser.getCity());

				UserEntity user = new UserEntity();
				user.setFirst_name(avroUser.getFirstname());
				user.setLast_name(avroUser.getLastname());
				user.setPassword("1!ZionTF");
				user.setPhone_number(avroUser.getPhonenumber());
				user.setCity(avroUser.getCity());
				user.setEmail(avroUser.getEmail());
				user.setUserid(UUID.randomUUID().toString());
				System.out.println("Saving person=" +  user);

				UserEntity savedPerson = userRepository.save(user);

			});
			return userStream;
		};
	}

//	@Transactional
//	@Bean
//	public Function<AvroUserEvent, AvroUserEvent> process_ride_requests() {
//		return avroUser -> {
//			System.out.println("Received event = " +  avroUser);
//			UserEntity user = new UserEntity();
//			user.setFirst_name(avroUser.getFirstname());
//			user.setLast_name(avroUser.getLastname());
//			user.setPassword("1!ZionTF");
//			user.setPhone_number(avroUser.getPhonenumber());
//			user.setCity(avroUser.getCity());
//			user.setEmail(avroUser.getEmail());
//
//			System.out.println("Saving person=" +  user);
//
//			UserEntity savedPerson = userRepository.save(user);
//			return avroUser;
//		};
//	}

//	@Bean
//	public ListenerContainerCustomizer<AbstractMessageListenerContainer<byte[], byte[]>> customizer() {
//		// Disable retry in the AfterRollbackProcessor
//		return (container, destination, group) -> container.setAfterRollbackProcessor(
//				new DefaultAfterRollbackProcessor<byte[], byte[]>(
//						(record, exception) -> System.out.println("Discarding failed record: " + record),
//						new FixedBackOff(0L, 0)));
//	}


}
