package com.ecom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecom.entity.OrderDetail;
import com.ecom.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {
	@Query(nativeQuery = true, value = "SELECT is_enabled FROM User a where a.email_id=:emailId")
	public boolean findByEmailId(@Param("emailId") String emailId);
}
