package com.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecom.configuration.JwtRequestFilter;
import com.ecom.dao.CartDao;
import com.ecom.dao.OrderDetailDao;
import com.ecom.dao.ProductDao;
import com.ecom.dao.TechnicianDao;
import com.ecom.dao.TicketDao;
import com.ecom.dao.UserDao;
import com.ecom.entity.Cart;
import com.ecom.entity.Product;
import com.ecom.entity.Support;
import com.ecom.entity.Technician;
import com.ecom.entity.Ticket;
import com.ecom.entity.User;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CartDao cartDao;
	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private TechnicianDao technicianDao;
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	public Product addNewProduct(Product product) {
		return productDao.save(product);		
	}
	
	public List<Product> getAllProducts(int pageNumber, String searchKey){
		Pageable pageable = PageRequest.of(pageNumber, 8);
		
		if(searchKey.equals("")) {
			return (List<Product>) productDao.findAll(pageable);
		}else {
			return (List<Product>)productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
		}
		
	}
	
	public void deleteProductDetails(Integer productId) {
		orderDetailDao.deleteByCity(productId);
		productDao.deleteById(productId);
	}

	public Product getProductDetailsById(Integer productId) {
		
		return productDao.findById(productId).get();
	}
	
	public List<Product> getProductDetails(boolean isSingeProductCheckout, Integer productId) {
	
		if(isSingeProductCheckout && productId != 0) {
			List<Product> list= new ArrayList<>();
			Product product = productDao.findById(productId).get();
			list.add(product);
			return list;
		}else {
		
			String username = JwtRequestFilter.CURRENT_USER;
			User user = userDao.findById(username).get();
			List<Cart>  carts= cartDao.findByUser(user);
			return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());			
		}
	}
	
	
	public String registerComplainService(Ticket ticket) {
		String message="Not able Complain Registered";
		try {
			String currentUser = JwtRequestFilter.CURRENT_USER;
			User user= userDao.findById(currentUser).get();
			Ticket complainEntity=new Ticket();
			complainEntity.setProductId(ticket.getProductId());
			complainEntity.setReason(ticket.getReason());
			complainEntity.setUserName(user.getUserName());
			complainEntity.setResolution("Yet to be provided");
			Technician techDataEntity=null;
			Optional<Technician> techData=technicianDao.findtechId().stream().findFirst();
			if(techData.isPresent()) {
				techDataEntity=techData.get();
			}
			
			ticketDao.save(complainEntity);
			complainEntity.setTechnicianId(techDataEntity.getTechnicianId());
			techDataEntity.setActiveTicket(techDataEntity.getActiveTicket()+1);
			technicianDao.save(techDataEntity);
			
			
			message="Complain Registered";
		}
		catch(Exception e) {
			
			message="Not able Complain Registered";
		}
		return message;
	}
	
	public String updateTicket(Support ticket) {
		String message="Not able Complain Registered";
		try {
			
			Ticket ticketSession=null;
			Optional<Ticket> ticketEntity=ticketDao.findById(String.valueOf(ticket.getTicket_Id()));
			if(ticketEntity.isPresent()) {
				ticketSession=ticketEntity.get();
			}
			ticketSession.setResolution(ticket.getResolution());
			ticketDao.save(ticketSession);
			
			
			
			message="Complain Registered";
		}
		catch(Exception e) {
			
			message="Not able Complain Registered";
		}
		return message;
	}
	
	

}
