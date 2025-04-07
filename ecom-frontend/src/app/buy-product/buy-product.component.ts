import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderDetails } from '../_model/order-details.model';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-buy-product',
  templateUrl: './buy-product.component.html',
  styleUrls: ['./buy-product.component.css']
})
export class BuyProductComponent implements OnInit {

  isSingleProductCheckout : string = "";
  priceReducerDerivation: string="";
  alertMessage : string ="";
  productDetails : Product[]=[];
  orderDetails: OrderDetails={
    fullName : '',
	  fullAddress: '',
	  contactNumber : '',
	  alternateContactNumber : '',
	  orderProductQuantityList : []
  }
  constructor( private activatedRoute: ActivatedRoute,
    private productService : ProductService,
    private router: Router) { }

  ngOnInit(): void {
    this.productDetails= this.activatedRoute.snapshot.data['productDetails'];

    this.isSingleProductCheckout = this.activatedRoute.snapshot.paramMap.get("isSingleProductCheckout");
    this.priceReducerDerivation = this.activatedRoute.snapshot.paramMap.get("priceReducerDerivation");
    //this.activatedRoute.snapshot.

    this.productDetails.forEach(
      x => this.orderDetails.orderProductQuantityList.push(
        {productId: x.productId, quantity: 1
        }
      )
    );
    console.log(this.productDetails);
    console.log(this.orderDetails);
  }

  public placeOrder(orderForm : NgForm){
    this.productService.placeOrder(this.orderDetails, this.isSingleProductCheckout).subscribe(
      (resp) => {
        console.log(resp);
        const result=resp;
        console.log(result);
        if(resp === 'Order could not be placed because availavle quantity is 0'){
          console.log(result+"Data");
          this.alertMessage='Order Can not be placed';
          orderForm.reset();
        }
        orderForm.reset();
        this.router.navigate(["/orderConfirm"])
      },
      (err) => {
        console.log(err);
      }
    );

  }

  getQuantityForProduct(productId){
    const filterProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId
    );
    return filterProduct[0].quantity;

  }

  getCalculatedTotal(productId, productDiscountedPrice){
    const filterProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId
    );
    return filterProduct[0].quantity*productDiscountedPrice;

  }

  onQuantityChanged(q, productId){
    this.orderDetails.orderProductQuantityList.filter(
      (orderProduct) => orderProduct.productId === productId
    )[0].quantity=q;
  }

  getCalculatedGrandTotal(priceReducerDerivation){
    let grandTotal = 0;
    
    this.orderDetails.orderProductQuantityList.forEach(
      (productQuantity) => {
        const price=this.productDetails.filter(product => product.productId === productQuantity.productId)[0].productDiscountedPrice
        var priceReducerDerivationAmount = parseInt(priceReducerDerivation);
        const discountprice=productQuantity.quantity*priceReducerDerivationAmount;
        
        grandTotal+=(price*productQuantity.quantity);
        grandTotal=grandTotal-discountprice;
      }
    );
    return grandTotal;
  }

}
