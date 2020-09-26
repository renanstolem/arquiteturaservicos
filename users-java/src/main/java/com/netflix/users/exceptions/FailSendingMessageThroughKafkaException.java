package com.netflix.users.exceptions;

public class FailSendingMessageThroughKafkaException extends RuntimeException {

    public FailSendingMessageThroughKafkaException(String message) {
        super(message);
    }

}
