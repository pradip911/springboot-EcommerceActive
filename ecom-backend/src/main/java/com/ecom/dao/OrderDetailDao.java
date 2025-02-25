package com.ecom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecom.entity.OrderDetail;
import com.ecom.entity.User;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer>{
	@Query(nativeQuery = true, value = "SELECT * FROM order_detail a where a.order_Status !='Cancelled' and a.user_user_name=:user_user_name")
	public List<OrderDetail> findByUser(@Param("user_user_name") String user_user_name);
	
	
	@Query(nativeQuery = true, value = "SELECT * FROM order_detail a where a.user_user_name=:user_user_name")
	public List<OrderDetail> findByUserTrans(@Param("user_user_name") String user_user_name);
	
	
	@Query(nativeQuery = true, value = "SELECT order_id FROM order_detail a where a.order_Status !='Cancelled'")
	public List<Integer> findOrderIdDetails();

}
