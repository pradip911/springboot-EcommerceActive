import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-product-view-details',
  templateUrl: './product-view-details.component.html',
  styleUrls: ['./product-view-details.component.css']
})
export class ProductViewDetailsComponent implements OnInit {

  selectProductIndex = 0;
  product: Product;
  discountedprice: "";
  priceReducer: DoubleRange;
  priceReducerDerivation : boolean=false;

  constructor(private activatedRoute: ActivatedRoute, private router : Router,
    private productService: ProductService) { }

  ngOnInit(): void {

   this.product = this.activatedRoute.snapshot.data['product'];
    
  }

  changeIndex(index){
    this.selectProductIndex=index;
  }

  buyProduct(productId,priceReducerDerivation){
    this.router.navigate(['/buyProduct', {
      isSingleProductCheckout: true,priceReducerDerivation: priceReducerDerivation, id: productId
    }]);
  }

  addToCart(productId){
    this.productService.addToCart(productId).subscribe(
      (response) => {
        console.log(response);
      },(error) => {
        console.log(error)
      }
    )

  }

 
  applyCouponCode(couponCode,productId){
    this.productService.couponCodeApply(couponCode,productId).subscribe(
      (response) => {
       // console.log(response);
       if(response!='0.0'){
        this.priceReducer = response;
        this.priceReducerDerivation=true;
        console.log("this.priceReducerDerivation"+this.priceReducerDerivation);
       }
       
        console.log( this.priceReducer);
       // this.discountedprice.=response.toString
      },(error) => {
        console.log(error)
      }
    )
  }

}
