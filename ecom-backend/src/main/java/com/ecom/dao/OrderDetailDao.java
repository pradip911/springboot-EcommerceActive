package com.ecom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.entity.OrderDetail;
import com.ecom.entity.User;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer>{
	@Query(nativeQuery = true, value = "SELECT * FROM order_detail a where a.order_Status !='Cancelled' and a.user_user_name=:user_user_name")
	public List<OrderDetail> findByUser(@Param("user_user_name") String user_user_name);
	
	@Query(nativeQuery = true, value = "SELECT * FROM order_detail a where a.order_Status !='Cancelled' and a.seller_email_id=:sellerEmailId")
	public List<OrderDetail> findBySellerEmailId(@Param("sellerEmailId") String sellerEmailId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM order_detail a where a.order_Status !='Cancelled' and a.seller_name=:sellerName")
	public List<OrderDetail> findBySellerName(@Param("sellerName") String sellerName);
	
	@Query(nativeQuery = true, value = "SELECT * FROM order_detail a where a.user_user_name=:user_user_name")
	public List<OrderDetail> findByUserTrans(@Param("user_user_name") String user_user_name);
	
	
	@Query(nativeQuery = true, value = "SELECT order_id FROM order_detail a where a.order_Status !='Cancelled'")
	public List<Integer> findOrderIdDetails();
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM order_detail WHERE product_product_id = ?1", nativeQuery = true)
	public void deleteByCity(Integer product_prodcut_id);
	
	
	@Modifying
	@Transactional
	@Query(value = "Update order_detail set order_status='Cancelled' WHERE order_id = ?1", nativeQuery = true)
	public void cancelOrder(Integer orderId);
	
	@Modifying
	@Transactional
	@Query(value = "Update order_detail set order_status='RequestForRefund' WHERE order_id = ?1", nativeQuery = true)
	public void refundOrder(Integer orderId);
	
	
	@Modifying
	@Transactional
	@Query(value = "Update order_detail set order_status=:status WHERE order_id =:orderId", nativeQuery = true)
	public void changeOrderStatus(Integer orderId,String status);

}
