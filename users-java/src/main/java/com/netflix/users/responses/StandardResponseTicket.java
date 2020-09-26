package com.netflix.users.responses;

import com.netflix.users.models.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponseTicket {

    private Ticket data;
}
