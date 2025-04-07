package com.ecom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.configuration.JwtRequestFilter;
import com.ecom.dao.CartDao;
import com.ecom.dao.FavItemsDao;
import com.ecom.dao.ProductDao;
import com.ecom.dao.UserDao;
import com.ecom.entity.Cart;
import com.ecom.entity.FavItems;
import com.ecom.entity.Product;
import com.ecom.entity.User;

@Service
public class FavItemsService {
	
	@Autowired
	private FavItemsDao favItemsDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	public void deleteFavItems(Integer cartId) {
		favItemsDao.deleteById(cartId);
	}
	
	public FavItems addFavItemTocart(Integer productId) {
		
		Product product = productDao.findById(productId).get();
		
		String username = JwtRequestFilter.CURRENT_USER;
		
		User user = null;
		
		if(username != null) {
			user = userDao.findById(username).get();
			
		}
		
		List<FavItems> cartList = favItemsDao.findByUser(user);
		List<FavItems> filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());
		
		if(filteredList.size() > 0) {
			return null;
		}
		
		
		if(product != null && user != null) {
			FavItems cart = new FavItems(product, user);
			return favItemsDao.save(cart);
		}
		return null;
	}
	
	public List<FavItems> getFavItemCartDetails(){
		String username = JwtRequestFilter.CURRENT_USER;
		User user = userDao.findById(username).get();
		return favItemsDao.findByUser(user);
		
	}
	
	

}
