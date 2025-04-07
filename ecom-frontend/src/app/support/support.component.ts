import { Component, OnInit } from '@angular/core';
import { MyOrderDetails } from '../_model/order.model';
import { ProductService } from '../_services/product.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Support } from '../_model/support.model';
import { NgForm } from '@angular/forms';
import { Product } from '../_model/product.model';


@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements OnInit {

  displayedColumns = ["Name", "Address" , "Contact No" , "Amount" , "Status", "Actions"];
  showRefundButton = false;
   support: Support = {
     resolution: "",
     product_Id: 0,
     technician_Id: 0,
     user_name: "",
     ticket_Id: 0,
     reason: ""
   }

  myTicketDetails: Support[] =[];
  constructor(private productService : ProductService) { }

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails(){
    this.productService.getTicket().subscribe(
      (resp: Support[]) => {
        console.log(resp);
        //this.myOrderDetails.orderAmount;
        this.myTicketDetails = resp;
        
      }, (err) => {
        console.log(err);
      }
    )
  }

   addSupportTicket(tiicket_Id: String,resolution: String){
      //const productFormData = this.prepareFormData(this.product);
      this.productService.addTicket(tiicket_Id,resolution).subscribe(
        (response: Support)=>{
         // productForm.reset();
         // this.product.productImages = [];
        },
        (error: HttpErrorResponse)=>{
          console.log(error)
        }
        );
      
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
}
