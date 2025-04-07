//import { componentFactoryName } from '@angular/compiler';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddNewProductComponent } from './add-new-product/add-new-product.component';
import { AdminComponent } from './admin/admin.component';
import { BuyProductResolverService } from './buy-product-resolver.service';
import { BuyProductComponent } from './buy-product/buy-product.component';
import { CartComponent } from './cart/cart.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { OrderConfirmationComponent } from './order-confirmation/order-confirmation.component';
import { OrderDetaisComponent } from './order-detais/order-detais.component';
import { ProductResolveService } from './product-resolve.service';
import { ProductViewDetailsComponent } from './product-view-details/product-view-details.component';
import { RegisterComponent } from './register/register.component';
import { ShowProductDetailesComponent } from './show-product-detailes/show-product-detailes.component';
import { UserComponent } from './user/user.component';
import { AuthGuard } from './_auth/auth.guard';
import { OrderUpdateComponent } from './order-update/order-update.component';
import { MyTransactionComponent } from './my-transaction/my-transaction.component';
import { RegisterSellerComponent } from './register-seller/register-seller.component';
import { SellerComponent } from './seller/seller.component';
import { MyOrdersSellerComponent } from './my-order-seller/my-orders-seller.component';
import { FavProductComponent } from './fav-product/fav-product.component';
import { CustomerCareComponent } from './customer-care/customer-care.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'admin', component: AdminComponent, canActivate:[AuthGuard], data:{roles:['Admin']} },
  { path: 'user', component: UserComponent ,  canActivate:[AuthGuard], data:{roles:['User']} },
  { path: 'login', component: LoginComponent },
  { path: 'forbidden', component: ForbiddenComponent },
  { path: 'addNewProduct', component: AddNewProductComponent , canActivate:[AuthGuard], data:{roles:['Admin']},
     resolve: {
            product: ProductResolveService
          }},
  { path: 'showProductDetailes' , component: ShowProductDetailesComponent ,  canActivate:[AuthGuard], data:{roles:['Admin']}},
  { path: 'orderInformation' , component: OrderDetaisComponent ,  canActivate:[AuthGuard], data:{roles:['Admin','User','Seller']}},
  { path: 'orderUpdate' , component: OrderUpdateComponent ,  canActivate:[AuthGuard], data:{roles:['Admin']}},
  { path: 'productViewDetails', component: ProductViewDetailsComponent, resolve: { product: ProductResolveService }},
  { path: 'buyProduct' , component: BuyProductComponent, canActivate:[AuthGuard], data:{roles:['User']},
  resolve: {
         productDetails: BuyProductResolverService} },
  { path: 'cart' , component: CartComponent, canActivate:[AuthGuard], data:{roles:['User']} },
  { path: 'orderConfirm', component: OrderConfirmationComponent ,  canActivate:[AuthGuard], data:{roles:['User']} },

  { path: 'myOrders', component: MyOrdersComponent ,  canActivate:[AuthGuard], data:{roles:['User','Seller']} },
  { path: 'register', component: RegisterComponent },
  { path: 'myTransaction' , component: MyTransactionComponent ,  canActivate:[AuthGuard], data:{roles:['User']}},
  { path: 'registerSeller' , component: RegisterSellerComponent ,  canActivate:[AuthGuard], data:{roles:['Admin']}},
  { path: 'seller' , component: SellerComponent ,  canActivate:[AuthGuard], data:{roles:['Seller']}},
  { path: 'myOrderSeller' , component: MyOrdersSellerComponent ,  canActivate:[AuthGuard], data:{roles:['Seller']}},
  { path: 'myFavItem' , component: FavProductComponent ,  canActivate:[AuthGuard], data:{roles:['User']}},
  { path: 'customerCare' , component: CustomerCareComponent ,  canActivate:[AuthGuard], data:{roles:['User']}},
  { path: 'openTicket' , component: CustomerCareComponent ,  canActivate:[AuthGuard], data:{roles:['Support']}},
  { path: 'closeTicket' , component: CustomerCareComponent ,  canActivate:[AuthGuard], data:{roles:['Support']}},




  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
