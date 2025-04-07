package com.ecom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Technician {

    @Id
    private int technicianId;
    private String name;
    private int activeTicket;
    private int closedTicket;
	public int getTechnicianId() {
		return technicianId;
	}
	public void setTechnicianId(int technicianId) {
		this.technicianId = technicianId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getActiveTicket() {
		return activeTicket;
	}
	public void setActiveTicket(int activeTicket) {
		this.activeTicket = activeTicket;
	}
	public int getClosedTicket() {
		return closedTicket;
	}
	public void setClosedTicket(int closedTicket) {
		this.closedTicket = closedTicket;
	}

	

}
