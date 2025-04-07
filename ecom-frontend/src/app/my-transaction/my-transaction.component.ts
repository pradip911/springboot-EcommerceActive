import { Component, OnInit } from '@angular/core';
import { MyOrderDetails } from '../_model/order.model';
import { ProductService } from '../_services/product.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-transaction.component.html',
  styleUrls: ['./my-transaction.component.css']
})
export class MyTransactionComponent implements OnInit {

  displayedColumns = ["Name", "Address" , "Contact No" , "Amount" , "Status" ,"Actions"];

  myOrderDetails: MyOrderDetails[] =[];
  constructor(private productService : ProductService) { }

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails(){
    this.productService.getMyTransaction().subscribe(
      (resp: MyOrderDetails[]) => {
        console.log(resp);
        this.myOrderDetails = resp;
      }, (err) => {
        console.log(err);
      }
    )
  }
  downloadPdf(orderId,orderFullName,orderStatus){
        this.productService.downloadPdf(orderId,orderFullName,orderStatus).subscribe(
          (resp)=> {
           // this.getAllProducts();
          },
          (error: HttpErrorResponse) => {
            console.log(error);}
        );    
      }


}
