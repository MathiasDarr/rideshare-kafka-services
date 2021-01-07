		final Map<String, String> serdeConfig = Collections.singletonMap(
				AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

		final SpecificAvroSerde<AvroPurchaseEvent> purchaseEventSerde = new SpecificAvroSerde<>();
		purchaseEventSerde.configure(serdeConfig, false);

		builder.table("purchases", Consumed.with(Serdes.Integer(),purchaseEventSerde), Materialized.as("purchases-store"));
