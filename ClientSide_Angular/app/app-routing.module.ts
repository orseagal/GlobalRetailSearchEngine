import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutUsComponent } from './about-us/about-us.component';
import { AdminAuthGuard } from './admin/admin.auth-guard';
import { AdminComponent } from './admin/admin.component';
import { CreateCustomersComponent } from './admin/customers/create-customers/create-customers.component';
import { CustomersListComponent } from './admin/customers/customers-list/customers-list.component';
import { EditCustomersComponent } from './admin/customers/edit-customers/edit-customers.component';
import { LoginComponent } from './auth/login.component';
import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { ContactComponent } from './contact/contact.component';
import { EbayComponent } from './ebay/ebay.component';
import { KijijiComponent } from './kijiji/kijiji.component';
import { MercadolibreComponent } from './mercadolibre/mercadolibre.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { SearchesComponent } from './searches/searches.component';
import { UserPasswordChangeComponent } from './user/password-change/user-password-change.component';
import { UserProfileComponent } from './user/profile/user-profile.component';
import { UserAuthGuard } from './user/user.auth-guard';
import { UserComponent } from './user/user.component';

const routes: Routes = [

  {path: '' , redirectTo:'about-us', pathMatch:'full'},
  {path: 'searches', redirectTo:'searches/ebay', pathMatch:'full'},
  {path: 'searches', component:SearchesComponent ,canActivate:[UserAuthGuard],children:[
  {path: 'ebay', component:EbayComponent},
  {path: 'mercadolibre' , component:MercadolibreComponent},
  {path: 'kijiji' , component:KijijiComponent},
    // {path: 'mercadolibre' , component:MercadolibreComponent}
  ]},


{path: 'login' , component:LoginComponent},
{path: 'signup' , component:SignUpComponent},
{path: 'about-us' , component: AboutUsComponent},
{path: 'contact' , component: ContactComponent},
{path: 'page-not-found', component:PageNotFoundComponent},


//----Admin----
{path: 'admin' , component: AdminComponent,canActivate:[AdminAuthGuard],children:[
  {path:'customers-list' , component: CustomersListComponent},
  {path: 'create-customers', component: CreateCustomersComponent},
  {path: 'edit-customers', component: EditCustomersComponent}, 
]

},
//----Customer----
{path: 'customer' , component: UserComponent,canActivate:[UserAuthGuard], children:[
{path:'profile', component: UserProfileComponent},
{path:'password-change', component:UserPasswordChangeComponent},

]

},


{path: '**',redirectTo:'about-us' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
