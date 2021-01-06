package sample.producer1.config;

import org.mddarr.orders.event.dto.AvroOrder;
import org.mddarr.orders.event.dto.FirstOrder;
import org.mddarr.rides.event.dto.AvroRideRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sample.producer1.models.RideRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class QueueConfig {

    @Bean
    BlockingQueue<RideRequest> getBlockingQueueunbounded() {
      return  new LinkedBlockingQueue<>();
    }

}
