import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderDetails } from '../_model/order-details.model';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';
import { CustomerCareModel } from '../_model/customer-care.model';
import { Reason } from "../_model/reason-enum.model";

@Component({
  selector: 'app-customer-care',
  templateUrl: './customer-care.component.html',
  styleUrls: ['./customer-care.component.css']
})
export class CustomerCareComponent implements OnInit {
  
  
  isSingleProductCheckout : string = "";
  priceReducerDerivation: string="";
  alertMessage : string ="";
  productDetails : Product[]=[];
  public ReasonEnum = Reason;
  public Reason1 = Reason.Reason1;
  public Reason2 = Reason.Reason2;
  public Reason3 = Reason.Reason3;


  customerCare: CustomerCareModel={
    reason : '',
	  productId: 0
  }
currentPokemon: any;
value: any;
SomeOptions: any;
option: any;
  constructor( private activatedRoute: ActivatedRoute,
    private productService : ProductService,
   
    private router: Router) { }

  ngOnInit(): void {
    //this.productDetails= this.activatedRoute.snapshot.data['productDetails'];

    //this.isSingleProductCheckout = this.activatedRoute.snapshot.paramMap.get("isSingleProductCheckout");
    //this.priceReducerDerivation = this.activatedRoute.snapshot.paramMap.get("priceReducerDerivation");
    //this.activatedRoute.snapshot.

   
    console.log(this.productDetails);
    //console.log(this.orderDetails);
  }
  onSelection() {
    console.log(this.customerCare.reason);    
    }
    selectedDay: string = '';

  //event handler for the select element's change event
  selectChangeHandler (event: any) {
    //update the ui
    this.selectedDay = event.target.value;
  }
  login(complainForm: NgForm) {
    this.productService.registerComplain(complainForm.value).subscribe(
      (response: any) => {
        

        const role = response.user.role[0].roleName;
        const message=response.message;
        if(message ==='User is Active'){
        if (role === 'Admin') {
          this.router.navigate(['/admin']);
        } else if(role === 'User'){
          this.router.navigate(['/user']);
        }
        else {
          this.router.navigate(['/seller']);
        }
      }
      else{
        this.router.navigate(['/login']);
      }
      },
      (error) => {
        console.log(error);
      }
    );
  }


}
