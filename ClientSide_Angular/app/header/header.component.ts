import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { DataStorageService } from '../shared/data-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
isAuthenticated = false;
cartItemsCount:number=0;
userType="";
userName="";
authLoginName:string;

  constructor(private authService: AuthService, private router: Router ,
               private dataStorageService: DataStorageService) { }

  ngOnInit() {
    this.authService.user.subscribe(user=>{
      if(user!=null){
        this.authLoginName= user.username;
        console.log(this.authLoginName);
      }
    });
    
    
    this.authService.user.subscribe(user=>{
      //this.isAuthenticated= !!user.username;
      this.isAuthenticated= user.isAuthenticated;
      if(user.authority!='ROLE_GUEST'){
        this.userType= user.authority;
        this.userName= user.username;
        console.log(this.userType);
      }else{
        this.userType='ROLE_GUEST';
        this.userName='guest';
      }
      

      console.log(!!user);
    })
   
  }

  onLogOut(){
   this.authService.logout();
  }


onSearch(searchValue:string){

console.log(searchValue);
if(searchValue.length==0){
  this.router.navigate(['products/products']);
}
this.router.navigate(['products/products', {search: searchValue}]);

}

}
