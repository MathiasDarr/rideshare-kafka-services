package org.mddarr.users.service;

import org.apache.kafka.streams.kstream.*;

import avro.AvroUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class UsersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersServiceApplication.class, args);
	}

	@Bean
	public Consumer<KStream<String, AvroUser>> process_users() {
		return (userKStream -> {

			userKStream.foreach((k,v)->{
				System.out.println("THE PROUDCT IS " + v.getFirstName());
			});

//			final Map<String, String> serdeConfig = Collections.singletonMap(
//					AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//
//			final SpecificAvroSerde<AvroPurchaseEvent> avroInventorySerde = new SpecificAvroSerde<>();
//			avroInventorySerde.configure(serdeConfig, false);
//
//			final SpecificAvroSerde<AvroProductID> avroProductSerde = new SpecificAvroSerde<>();
//			avroInventorySerde.configure(serdeConfig, false);
//
//			KGroupedStream<String, AvroPurchaseEvent> groupedByProductID = purchaseEventKStream.groupBy((key, value)->key, Grouped.with(Serdes.String(),avroInventorySerde));
//
//			purchaseEventKStream.foreach((k,v)->{
//				System.out.println("THE PROUDCT2 IS " + v.getProductid());
//			});
//
//			final KTable<String, Long> productPurchaseCountsTable = groupedByProductID.count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as(Constants.PURCHASE_COUNT_STORE)
//					.withKeySerde(Serdes.String())
//					.withValueSerde(Serdes.Long()));
//
//			productPurchaseCountsTable.toStream().foreach((k,v) -> {
//				System.out.println("THE PURCHASE COUNT TABLE LOOIKS LIKE " + k + " and the number of purchases is " + v);
//			} );

		});

	}


}
