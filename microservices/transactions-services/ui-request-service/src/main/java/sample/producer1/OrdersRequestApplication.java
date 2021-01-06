package sample.producer1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

import org.mddarr.orders.event.dto.AvroOrder;
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
import sample.producer1.models.RideRequest;

@SpringBootApplication
@EnableSchemaRegistryClient
public class OrdersRequestApplication {

	@Autowired
	BlockingQueue<AvroOrder> unbounded;

	public static void main(String[] args) {
		SpringApplication.run(OrdersRequestApplication.class, args);
	}


	@Bean
	public Supplier<AvroRideRequest> orders_supplier() {
		return () -> { AvroOrder rideRequest = unbounded.poll();
				AvroRideRequest avroRideRequest = AvroRideRequest.newBuilder()
						.setRequestId("dfdf")
						.setDestination("dfdf")
						.setUserId("dfdf")
						.setRiders(2)
						.build();
				return avroRideRequest;
		};
	}

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



