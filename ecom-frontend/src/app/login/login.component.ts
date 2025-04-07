import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private userService: UserService,
    private userAuthService: UserAuthService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  login(loginForm: NgForm) {
    this.userService.login(loginForm.value).subscribe(
      (response: any) => {
        this.userAuthService.setRoles(response.user.role);
        this.userAuthService.setToken(response.jwtToken);

        const role = response.user.role[0].roleName;
        const message=response.message;
        if(message ==='User is Active'){
        if (role === 'Admin') {
          this.router.navigate(['/admin']);
        } else if(role === 'User'){
          this.router.navigate(['/user']);
        }
        else if(role=='Support'){
          this.router.navigate(['/support']);
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

  registerUser(){
    this.router.navigate(['/register']);
  }
}
