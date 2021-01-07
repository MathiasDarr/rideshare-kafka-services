package sample.producer1;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

import org.mddarr.rides.event.dto.AvroRideRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.schema.registry.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.schema.registry.client.EnableSchemaRegistryClient;
import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.util.IntegrationReactiveUtils;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import sample.producer1.models.RideRequest;

@SpringBootApplication
@EnableSchemaRegistryClient
public class RideRequestApplication {

	@Autowired
	BlockingQueue<RideRequest> unbounded;

	public static void main(String[] args) {
		SpringApplication.run(RideRequestApplication.class, args);
	}

//
//	@Bean
//	public Supplier<Flux<Message<AvroRideRequest>>> orders_supplier(){
//
//	}
//
//	@Bean
//	public Supplier<AvroRideRequest> orders_supplier() {
//		return () -> {
//			try {
//				IntegrationReactiveUtils.messageChannelToFlux()
//				RideRequest rideRequest = unbounded.take();
//
//				System.out.println("THE RIDE REUQEST " + rideRequest.getDestination());
//				System.out.println("THE RIDE REUQEST " + rideRequest.getRiders());
//				System.out.println("THE RIDE REUQEST " + rideRequest.getCity());
//				System.out.println("THE RIDE REUQEST " + rideRequest.getUserid());
//
//				AvroRideRequest avroRideRequest = AvroRideRequest.newBuilder()
//						.setRequestId(UUID.randomUUID().toString())
//						.setDestination(rideRequest.getDestination())
//						.setUserId(rideRequest.getUserid())
//						.setRiders(rideRequest.getRiders())
//						.build();
//				return avroRideRequest;
//			} catch (InterruptedException e) {
//
//				e.printStackTrace();
//				return null;
//			}
//
//
//		};
//	}

	@Configuration
	static class ConfluentSchemaRegistryConfiguration {
		@Bean
		public SchemaRegistryClient schemaRegistryClient(@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint){
			ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
			client.setEndpoint(endpoint);
			return client;
		}
	}
}



