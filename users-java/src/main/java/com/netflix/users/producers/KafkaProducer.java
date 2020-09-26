package com.netflix.users.producers;

import com.netflix.users.models.Ticket;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class KafkaProducer {
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.producer}")
    private String topic;

    KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(JSONObject message) {
        this.kafkaTemplate.send(topic, message.toString());
        logger.info("Sent message [" + message + "] to " + topic);
    }

}
