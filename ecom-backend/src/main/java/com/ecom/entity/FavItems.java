package com.ecom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class FavItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fav_itemsid")
	private Integer favItemsid;
	@OneToOne
	private Product product;
	@OneToOne
	private User user;
	
	
	
	public FavItems() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public FavItems(Product product, User user) {
		super();
		this.product = product;
		this.user = user;
	}


	
	public Integer getFavItemsid() {
		return favItemsid;
	}


	public void setFavItemsid(Integer favItemsid) {
		this.favItemsid = favItemsid;
	}


	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

}

