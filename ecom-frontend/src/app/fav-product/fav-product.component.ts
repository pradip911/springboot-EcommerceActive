import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-fav-product',
  templateUrl: './fav-product.component.html',
  styleUrls: ['./fav-product.component.css']
})
export class FavProductComponent implements OnInit {

  displayedColumns: string[] = ['Name', 'Description', 'Price' , 'Discounted Price' ,'Action'];
  cartDetails : any[] = [];

  constructor(private productService : ProductService,
    private router : Router) { }

  ngOnInit(): void {
    this.getCartDetails();
  }

  delete(cartId){
    console.log(cartId)
    this.productService.deleteFavItem(cartId).subscribe(
      (resp) => {
        console.log(resp);
        this.getCartDetails();

      },(error) =>{
        console.log(error);
      }
    )
  }

  getCartDetails(){

    this.productService.getFavItemDetails().subscribe(
      (response : any[]) => {
        console.log(response)
        this.cartDetails = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  checkout(){
    this.router.navigate(['/buyProduct', {
      isSingleProductCheckout: false, id: 0
    }]);
    // this.productService.getProductDetails(false, 0).subscribe(
    //   (resp) => {
    //     console.log(resp);
    //   },(error) =>{
    //     console.log(error);
    //   }
    // );
  }

}
