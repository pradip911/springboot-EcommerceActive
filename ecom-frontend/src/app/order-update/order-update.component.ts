import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { FileHandel } from '../_model/file-handel.model';
import { Product } from '../_model/product.model';
import { OrderStatusUpdateEntity } from '../_model/order-status-update-entity.model';

@Component({
  selector: 'app-order-update',
  templateUrl: './order-update.component.html',
  styleUrls: ['./order-update.component.css']
})
export class OrderUpdateComponent implements OnInit {
  isNewProduct = true;
  orderStatusUpdateEnity: OrderStatusUpdateEntity = {
    orderId: null,
    status: "",
  }
  public data ;
  constructor(private productService: ProductService, 
    private sanitizer: DomSanitizer,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
   // this.orderStatusUpdateEnity = this.activatedRoute.snapshot.data['product'];

   this.getCurrencies();

  }

  addProduct(loginForm: NgForm){

  //  // const productFormData = this.prepareFormData(this.orderStatusUpdate);
  //  const orderProductStatus = this.prepareFormData(this.orderStatusUpdate);
  //   this.productService.updateProductStatus(orderProductStatus).subscribe(
  //     (response: OrderStatusUpdate)=>{
  //       productForm.reset();
  //      // this.orderStatusUpdate.productImages = [];
  //     },
  //     (error: HttpErrorResponse)=>{
  //       console.log(error)
  //     }
  //     );

  this.productService.updateProductStatus(loginForm.value).subscribe(
    (resp) => {
      console.log(resp);
      loginForm.reset();
     // this.router.navigate(["/orderConfirm"])
    },
    (err) => {
      console.log(err);
    }
  );
    
  }


  getCurrencies() {
    this.productService.getOrderId().subscribe
    // (
    //   (resp) => {
    //     console.log(resp);
    //    //
    //    //  this.dataSource = resp;
    //   }, (error) => {
    //     console.log(error);
    //   }
    // );

    (res => { this.data  = res });

  }

  prepareFormData(product: OrderStatusUpdateEntity): FormData {
    const formData = new FormData();

    formData.append(
      'product',
      new Blob([JSON.stringify(product)], {type: 'application/json'})
    );

    
    return formData;
  } 
}
