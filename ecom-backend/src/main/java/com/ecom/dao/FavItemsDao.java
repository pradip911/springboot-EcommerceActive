package com.ecom.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.entity.FavItems;
import com.ecom.entity.User;

@Repository
public interface FavItemsDao extends CrudRepository<FavItems, Integer>{
	
	public List<FavItems> findByUser(User user);

}
