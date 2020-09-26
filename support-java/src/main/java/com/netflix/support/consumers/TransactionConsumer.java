package com.netflix.support.consumers;

import com.netflix.support.services.TicketService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class TransactionConsumer {

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    TicketService service;

    @KafkaListener(topics = "${spring.kafka.topic.consumer}")
    public void processMessage(String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) throws Exception {
        logger.info("Message received successfully");
        JSONObject object = new JSONObject(message);
        service.process(object);
    }
}