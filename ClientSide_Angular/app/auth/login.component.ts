import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { Observable, throwError } from 'rxjs';
import { UserLogin } from '../models/userLogin';
import { AuthService } from './auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DataStorageService } from '../shared/data-storage.service';
import { MatConfirmDialogComponent } from '../shared/mat-confirm-dialog/mat-confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginRequestPayload } from '../models/LoginRequestPayload';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
 
  isLoading=false;
  error:string=null;
  isRememberMe=false;
  loginForm: FormGroup;
  loginRequestPayload: LoginRequestPayload;
  registerSuccessMessage: string="";
  isError: boolean;
  
  constructor(private authService: AuthService,private router:Router,private dataStorageService:DataStorageService,private dialog: MatDialog 
    , private snackBar: MatSnackBar,private toastr: ToastrService,private activatedRoute: ActivatedRoute) { 
      this.loginRequestPayload = {
        username: '',
        password: ''
      };
    }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });

    this.activatedRoute.queryParams
      .subscribe(params => {
        if (params.registered !== undefined && params.registered === 'true') {
          this.toastr.success('Signup Successful');
          this.registerSuccessMessage = 'Please Check your inbox for activation email '
            + 'activate your account before you Login!';
        }
      });
  }
  login(){
    this.loginRequestPayload.username = this.loginForm.get('username').value;
    this.loginRequestPayload.password = this.loginForm.get('password').value;

    this.authService.login(this.loginRequestPayload).subscribe(data => {
      this.isError = false;
      this.router.navigate(['/searches']);
      this.toastr.success('Login Successful');
    },errorMessage=>{
      console.log(errorMessage);
      this.error=errorMessage;
      this.isLoading=false;
    });
    this.loginForm.reset();
  }

  onSignup(){
  this.router.navigate(['signup']);
  }

}






