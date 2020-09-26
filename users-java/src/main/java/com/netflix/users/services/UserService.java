package com.netflix.users.services;

import com.netflix.users.dto.TicketDTO;
import com.netflix.users.entities.User;
import com.netflix.users.exceptions.FailSendingMessageThroughKafkaException;
import com.netflix.users.models.Ticket;
import com.netflix.users.producers.KafkaProducer;
import com.netflix.users.repositories.UserRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    KafkaProducer producer;

    @Autowired
    UserRepository repository;

    public ResponseEntity saveTicket(Integer id, TicketDTO ticketDTO) {

        logger.info("Searching user...");

        Optional<User> user = repository.findById(id);

        if(!user.isPresent()) {
            logger.error("User not found!");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        logger.info("Send message through kafka...");

        Ticket ticket = new Ticket();
        ticket.setIdUser(id);
        ticket.setCategoryId(ticketDTO.getCategoryId());
        ticket.setDescription(ticketDTO.getDescription());

        try {
            producer.send(new JSONObject(ticket.toString()));
        } catch (Exception e) {
            logger.info("Fail sending message!");
            throw new FailSendingMessageThroughKafkaException(e.getMessage());
        }

        logger.info("Message sent successfully!");

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
