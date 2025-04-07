package com.ecom.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.entity.Ticket;

@Repository
public interface TicketDao extends CrudRepository<Ticket, String> {
	
}
