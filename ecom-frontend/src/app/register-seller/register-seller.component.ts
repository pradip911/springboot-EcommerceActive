import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-SellerRegister',
  templateUrl: './register-seller.component.html',
  styleUrls: ['./register-seller.component.css']
})
export class RegisterSellerComponent implements OnInit {

  constructor(private userService : UserService,
    private router : Router) { }

  ngOnInit(): void {
  }

  register(registerForm: NgForm){
    console.log(registerForm.value)
    this.userService.registerSeller(registerForm.value).subscribe(
      (response) => {
        this.router.navigate(['/login']);
      },
      (error) => {
        console.log(error);
      }
      );  

  }

}
