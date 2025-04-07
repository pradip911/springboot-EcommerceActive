package com.ecom.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.entity.FavItems;
import com.ecom.entity.ImageModel;
import com.ecom.entity.OrderDetail;
import com.ecom.entity.OrderStatusUpdateEntity;
import com.ecom.entity.Product;
import com.ecom.entity.Support;
import com.ecom.entity.Ticket;
import com.ecom.service.FavItemsService;
import com.ecom.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FavItemsService favItemsService;
	
	
	
	@PreAuthorize("hasRole('Admin')")
	@PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Product addNewProduct(@RequestPart("product") Product product,
									@RequestPart("imageFile") MultipartFile[] file) {		
	
		try {
			Set<ImageModel> images = uplodImage(file);
			
			product.setProductImages(images);
			return productService.addNewProduct(product); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		
	}
	
	
	
	
	
	
	public Set<ImageModel> uplodImage(MultipartFile[] multipartFiles) throws IOException{
		
		Set<ImageModel> imageModels = new HashSet<>();
		
		for(MultipartFile file: multipartFiles) {
			ImageModel imageModel = new ImageModel(
					file.getOriginalFilename(),
					file.getContentType(),
					file.getBytes());
			
			imageModels.add(imageModel);
		}
		return imageModels;
	}
	
	
	@GetMapping({"/getAllProducts"})
	public List<Product> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber
			, @RequestParam(defaultValue = "") String searchKey){
		return productService.getAllProducts(pageNumber, searchKey);
	}
	
	
	@GetMapping({"/getProductDetailsById/{productId}"})
	public Product getProductDetailsById(@PathVariable("productId") Integer productId) {
		
		return productService.getProductDetailsById(productId);
		
	}
	
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping({"/deleteProductDetails/{productId}"})
	public void deleteProductDetailes(@PathVariable("productId") Integer productId) {
		productService.deleteProductDetails(productId);
	}
	
	@PreAuthorize("hasRole('User')")
	@GetMapping({"/getProductDetails/{isSingeProductCheckout}/{productId}"})
	public List<Product> getProductDetails(@PathVariable(name="isSingeProductCheckout") boolean isSingeProductCheckout,
										@PathVariable(name= "productId") Integer productId) {
		
		return productService.getProductDetails(isSingeProductCheckout, productId);
		
		
	}
	/*******fav Items COntroller******/
	
	@PreAuthorize("hasRole('User')")
	@GetMapping({"/addFavItemTocart/{productId}"})
	public FavItems addFavItemTocart(@PathVariable(name="productId") Integer productId) {
		return favItemsService.addFavItemTocart(productId);
		
	}
	@PreAuthorize("hasRole('User')")
	@DeleteMapping({"/deleteFavCartItem/{cartId}"})
	public void deleteFavCartItem(@PathVariable(name= "cartId") Integer cartId) {
		favItemsService.deleteFavItems(cartId);		
	}
	
	
	
	@PreAuthorize("hasRole('User')")
	@GetMapping({"/getFavItemCartDetails"})
	public List<FavItems> getFavItemCartDetails() {
		return favItemsService.getFavItemCartDetails();
		
	}
	
	/********fav Items Controller********/
	@PreAuthorize("hasRole('User')")
	@PostMapping({"/registerCompain"})
    public String registerCompplain(@RequestBody Ticket complain) {
		System.out.println("complain"+complain.getProductId()+"::"+complain.getReason());
		String message=productService.registerComplainService(complain);
        return message;
    }
	
	@PreAuthorize("hasRole('Support')")
	@PostMapping(value = {"/updateTicket"})
	public String updateOrderStatus(@RequestBody Support supportEntity) {		
		try {
			String orderDetails=productService.updateTicket(supportEntity);
			return orderDetails;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	

}
