import { Component, OnInit } from '@angular/core';
import { MyOrderDetails } from '../_model/order.model';
import { ProductService } from '../_services/product.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-my-orders-seller',
  templateUrl: './my-orders-seller.component.html',
  styleUrls: ['./my-orders-seller.component.css']
})
export class MyOrdersSellerComponent implements OnInit {

  displayedColumns = ["Name", "Address" , "Contact No" , "Amount" , "Status", "Actions"];
  showRefundButton = false;

  myOrderDetails: MyOrderDetails[] =[];
  constructor(private productService : ProductService) { }

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails(){
    this.productService.getMyOrders().subscribe(
      (resp: MyOrderDetails[]) => {
        console.log(resp);
        //this.myOrderDetails.orderAmount;
        this.myOrderDetails = resp;
        
      }, (err) => {
        console.log(err);
      }
    )
  }

   deleteProduct(productId){
      this.productService.deleteProduct(productId).subscribe(
        (resp)=> {
         // this.getAllProducts();
        },
        (error: HttpErrorResponse) => {
          console.log(error);}
      );    
    }

    cancelOrder(orderId){
      this.productService.cancelOrder(orderId).subscribe(
        (resp)=> {
         // this.getAllProducts();
        },
        (error: HttpErrorResponse) => {
          console.log(error);}
      );    
    }

    completeOrder(orderId){
      this.productService.cancelOrder(orderId).subscribe(
        (resp)=> {
         // this.getAllProducts();
        },
        (error: HttpErrorResponse) => {
          console.log(error);}
      );    
    }

    changeOrderStatus(orderId,status){
      this.productService.changeOrderStatus(orderId,status).subscribe(
        (resp)=> {
         // this.getAllProducts();
        },
        (error: HttpErrorResponse) => {
          console.log(error);}
      );    
    }


    refundOrder(orderId){
      this.productService.refundOrder(orderId).subscribe(
        (resp)=> {
         // this.getAllProducts();
        },
        (error: HttpErrorResponse) => {
          console.log(error);}
      );    
    }

}
