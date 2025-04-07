import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderDetails } from '../_model/order-details.model';
import { MyOrderDetails } from '../_model/order.model';
import { Product } from '../_model/product.model';
import { OrderStatusUpdateEntity } from '../_model/order-status-update-entity.model';
import { Support } from '../_model/support.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) { }

  public getAllOrderDetailsForAdmin() : Observable<MyOrderDetails[]>{
    return this.httpClient.get<MyOrderDetails[]>("http://localhost:9090/getAllOrderDetails");
   }

  public getMyOrders() : Observable<MyOrderDetails[]>{
    return this.httpClient.get<MyOrderDetails[]>("http://localhost:9090/getOrderDetails");
   }

   public getTicket() : Observable<Support[]>{
    return this.httpClient.get<Support[]>("http://localhost:9090/getTicket");
   }

   public getMyTransaction() : Observable<MyOrderDetails[]>{
    return this.httpClient.get<MyOrderDetails[]>("http://localhost:9090/getTransaction");
   }

  public deleteCartItem(cartId){
    return this.httpClient.delete("http://localhost:9090/deleteCartItem/"+cartId);
   }

  public addProduct(product: FormData){
    return this.httpClient.post<Product>("http://localhost:9090/addNewProduct", product);
  }

  public addTicket(ticket_Id,resolution){
    return this.httpClient.post<Support>("http://localhost:9090/updateTicket",ticket_Id+"/"+resolution);
  }

  public updateProductStatus(orderProductStatus){
    return this.httpClient.post<OrderStatusUpdateEntity>("http://localhost:9090/updateOrderStatus", orderProductStatus);
  }

  public registerComplain(complainForm) {
    return this.httpClient.post("http://localhost:9090/registerComplain", complainForm);
  }

  public getOrderId(){
    return this.httpClient.get("http://localhost:9090/updateOrderStatusDerived");
  }

  public getAllProducts(pageNumber, searchKeyword: string= ""){
    return this.httpClient.get<Product[]>("http://localhost:9090/getAllProducts?pageNumber="+pageNumber+"&searchKey="+searchKeyword);
  }

  
  public couponCodeApply(couponCode,productId){
    return this.httpClient.get("http://localhost:9090/applyCouponCode/"+couponCode+"/"+productId);
   }

  public getProductDetailsById(productId){
    return this.httpClient.get<Product>("http://localhost:9090/getProductDetailsById/"+productId);
   }

  public deleteProduct(productId: number){
   return this.httpClient.delete("http://localhost:9090/deleteProductDetails/"+productId);
  }

  public cancelOrder(orderId: number){
    return this.httpClient.get("http://localhost:9090/cancelOrder/"+orderId);
   }

   public completeOrder(orderId: number){
    return this.httpClient.get("http://localhost:9090/completeOrder/"+orderId);
   }

  

   public refundOrder(orderId: number){
    return this.httpClient.get("http://localhost:9090/refundOrder/"+orderId);
   }

   public changeOrderStatus(orderId: number,status: String){
    return this.httpClient.get("http://localhost:9090/changeOrderStatus/"+orderId+"/"+status);
   }
   public downloadPdf(orderId: number,orderFullName: string,orderStatus: string){
    return this.httpClient.get("http://localhost:9090/downloadPdf/"+orderId+"/"+orderFullName+"/"+orderStatus);
   }
  public getProductDetails(isSingeProductCheckout,productId){
    return this.httpClient.get<Product[]>("http://localhost:9090/getProductDetails/"+isSingeProductCheckout+"/"+productId);
   }


   public placeOrder(orderDetails: OrderDetails, isCartCheckout){
    return this.httpClient.post("http://localhost:9090/placeOrder/"+isCartCheckout, orderDetails);
   }

   public addToCart(productId){
    return this.httpClient.get("http://localhost:9090/addToCart/"+productId);
   }

   public getCartDetails(){
    return this.httpClient.get("http://localhost:9090/getCartDetails");
   }

   public getFavItemDetails(){
    return this.httpClient.get("http://localhost:9090/getFavItemCartDetails");
   }

   public addTofavItemDetails(productId){
    return this.httpClient.get("http://localhost:9090/addFavItemTocart/"+productId);
   }

   public deleteFavItem(productId){
    return this.httpClient.delete("http://localhost:9090/deleteFavCartItem/"+productId);
   }


}
