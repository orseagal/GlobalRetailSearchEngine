import { Component, OnInit } from '@angular/core';
import { Customer } from 'src/app/models/customer';
import { AuthService } from 'src/app/auth/auth.service';
import { UserLogin } from 'src/app/models/userLogin';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
 user:UserLogin;
 customer:String;

  constructor(private authService: AuthService) { }

  ngOnInit() {
  this.authService.user.subscribe(user=>{
    console.log(user)
    this.user= user;
  });
  }



}
