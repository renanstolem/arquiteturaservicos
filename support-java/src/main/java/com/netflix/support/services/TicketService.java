package com.netflix.support.services;

import com.netflix.support.entities.Ticket;
import com.netflix.support.repositories.TicketRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    TicketRepository repository;

    public void process(JSONObject object) {

        Ticket ticket = new Ticket();
        ticket.setIdUser(object.getInt("idUser"));
        ticket.setCategoryId(object.getInt("categoryId"));
        ticket.setDescription(object.getString("description"));

        logger.info("Saving ticket...");

        repository.save(ticket);

        logger.info("Ticket successfully saved");
    }
}
