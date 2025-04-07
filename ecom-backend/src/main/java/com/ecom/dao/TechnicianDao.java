package com.ecom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecom.entity.OrderDetail;
import com.ecom.entity.Role;
import com.ecom.entity.Technician;

@Repository
public interface TechnicianDao extends CrudRepository<Technician, String> {
	@Query(nativeQuery = true, value = "SELECT * FROM technician a where a.active_Ticket =0 and a.closed_Ticket>2")
	public List<Technician> findtechId();
}
