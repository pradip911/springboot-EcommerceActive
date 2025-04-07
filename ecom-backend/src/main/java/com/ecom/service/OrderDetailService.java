package com.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.configuration.JwtRequestFilter;
import com.ecom.dao.CartDao;
import com.ecom.dao.OrderDetailDao;
import com.ecom.dao.ProductDao;
import com.ecom.dao.UserDao;
import com.ecom.entity.Cart;
import com.ecom.entity.OrderDetail;
import com.ecom.entity.OrderInput;
import com.ecom.entity.OrderProductQuantity;
import com.ecom.entity.Product;
import com.ecom.entity.User;

@Service
public class OrderDetailService {
	
	private static final String ORDER_PLACED = "Placed";  
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CartDao cartDao;
	
	public List<OrderDetail> getAllOrderDetails(){
		List<OrderDetail> orderDetails = new ArrayList<>();
		orderDetailDao.findAll().forEach(e -> orderDetails.add(e));
		
		return orderDetails;
	} 
	
	public List<OrderDetail> getOrderDetails() {
		String currentUser = JwtRequestFilter.CURRENT_USER;
		User user = userDao.findById(currentUser).get();
		if(user.getUserName().contains("@seller")) {
			if(orderDetailDao.findBySellerEmailId(user.getSellerEmailId()).size()>0) {
				return orderDetailDao.findBySellerEmailId(user.getSellerEmailId());
			}
			else if(orderDetailDao.findBySellerName(user.getSellerEmailId()).size()>0) {
				return orderDetailDao.findBySellerName(user.getSellerEmailId());

			}
		}
		else {

		}
		return orderDetailDao.findByUser(user.getUserName());
	}
	
	public List<OrderDetail> getTrans() {
		String currentUser = JwtRequestFilter.CURRENT_USER;
		User user = userDao.findById(currentUser).get();
		
		return orderDetailDao.findByUserTrans(user.getUserName());
	}
	
	public String placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
		List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
		
		for(OrderProductQuantity o: productQuantityList) {
			Product product = productDao.findById(o.getProductId()).get();
			if(product.getProductQuantity()==0) {
				return new String("Order could not be placed because availavle quantity is 0");
			}
			int actualProductQuantity=product.getProductQuantity();
			int orderedProductQuantity=o.getQuantity();
			int setProductQuantity=0;
			if(orderedProductQuantity>actualProductQuantity) {
				setProductQuantity=actualProductQuantity;
			}
			
			String currentUser = JwtRequestFilter.CURRENT_USER;
			User user= userDao.findById(currentUser).get();
			
			OrderDetail orderDetail = new OrderDetail(
					orderInput.getFullName(),
					orderInput.getFullAddress(),
					orderInput.getContactNumber(),
					orderInput.getAlternateContactNumber(),
					ORDER_PLACED,
					product.getProductDiscountedPrice()*o.getQuantity(),
					product,
					user,product.getSellerName(),product.getSellerEmailId(),setProductQuantity);
			
			if(!isSingleProductCheckout) {
				List<Cart> carts= cartDao.findByUser(user);
				carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));			
				
			}
			orderDetailDao.save(orderDetail);
			product.setProductQuantity(actualProductQuantity-setProductQuantity);
			productDao.save(product);
						
		}
		return "Placed Order Sucessfully";
	}
	
	public Optional<OrderDetail> getOrderDetail(Integer orderId,String status) {
		Optional<OrderDetail> orderDetails=orderDetailDao.findById(orderId);
		if(orderDetails.isEmpty()) {
			return null;
		}
		else { 
			orderDetails.get().setOrderStatus(status);
			orderDetailDao.save(orderDetails.get());
		}
		return orderDetails;
		
	}
	
	
	public List<Integer> getOrderId() {
		List<Integer> orderDetails=orderDetailDao.findOrderIdDetails();
		if(orderDetails.isEmpty()) {
			return null;
		}
		return orderDetails;
		
	}
	
	public void cancelOrder(Integer orderId) {
		orderDetailDao.cancelOrder(orderId);
		
		
	}
	
	public void changeOrderStatus(Integer orderId,String status) {
		orderDetailDao.changeOrderStatus(orderId,status);
		
		
	}
	
	public void completeOrder(Integer orderId) {
		orderDetailDao.cancelOrder(orderId);
		
		
	}
	
	public void refundOrder(Integer orderId) {
		orderDetailDao.refundOrder(orderId);
		
		
	}
	
	public Double  applyCouponCode(String couponCode, Integer productId) {
		Optional<Product> product=productDao.findById(productId);
		Double discountedValue=0.0;
		if(product.isPresent() && null!=couponCode) {
			discountedValue=product.get().getProductDiscountedPrice()-125.00;
		}
		System.out.println("discountedValue"+discountedValue);
		return discountedValue;
	}
	
	

}
