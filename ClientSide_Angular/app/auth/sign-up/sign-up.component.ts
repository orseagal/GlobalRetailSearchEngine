import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { NgForm, FormGroup, FormBuilder, Validators} from '@angular/forms';
import { Observable } from 'rxjs';
import { UserLogin } from 'src/app/models/userLogin';
import { CustomValidators } from 'src/app/shared/custom-validators';
import { DataStorageService } from 'src/app/shared/data-storage.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatConfirmDialogComponent } from 'src/app/shared/mat-confirm-dialog/mat-confirm-dialog.component';
import { SignupRequestPayload } from 'src/app/models/signupRequestpayload';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  
  ngOnInit(){

  }
  public frmSignup: FormGroup;
  signupRequestPayload :SignupRequestPayload;
  isLoading=false;
  error:string=null;
  isRememberMe=false;
  userRole= "ROLE_CUSTOMER";

  constructor(private fb: FormBuilder, private authService: AuthService,private router:Router,
    private dataStorageService:DataStorageService,private toastr:ToastrService ,private dialog: MatDialog , private snackBar: MatSnackBar) {
    this.frmSignup = this.createSignupForm();
    this.signupRequestPayload={
      username:'',
      email:'',
      password:''
    }
  }

  createSignupForm(): FormGroup {
    return this.fb.group(
      {
        email: [
          null,
          Validators.compose([Validators.email, Validators.required])
        ],

        name: [
          null,
          Validators.compose([Validators.minLength(6), Validators.required])
        ],

        password: [
          null,
          Validators.compose([
            Validators.required,
            // check whether the entered password has a number
            CustomValidators.patternValidator(/\d/, {
              hasNumber: true
            }),
            // check whether the entered password has upper case letter
            CustomValidators.patternValidator(/[A-Z]/, {
              hasCapitalCase: true
            }),
            // check whether the entered password has a lower case letter
            CustomValidators.patternValidator(/[a-z]/, {
              hasSmallCase: true
            }),
           
         
            Validators.minLength(8)
          ])
        ],
        confirmPassword: [null, Validators.compose([Validators.required])]
      },
      {
        // check whether our password and confirm password match
        validator: CustomValidators.passwordMatchValidator
      }
    );
  }

  signup() {
    // do signup or something

    console.log(this.frmSignup.value);

    if(!this.frmSignup.valid){
      return;
    }
    this.signupRequestPayload.email= this.frmSignup.value.email;
    this.signupRequestPayload.email = this.signupRequestPayload.email.trim();
    this.signupRequestPayload.password= this.frmSignup.value.password;
    this.signupRequestPayload.password= this.signupRequestPayload.password.trim();
    this.signupRequestPayload.username = this.frmSignup.value.name;
    this.signupRequestPayload.username  = this.signupRequestPayload.username.trim();
    let authObs: Observable<number>
    this.isLoading=true;
    authObs= this.authService.signUp(this.signupRequestPayload);

    authObs.subscribe(resData=>{
      console.log(resData)
      this.isLoading=false;
      this.error=null;
      this.router.navigate(['/login'],
      { queryParams: { registered: 'true' } });
      //this.authService.routeClient();
    },errorMessage=>{
      console.log(errorMessage);
      this.error=errorMessage;
      this.isLoading=false;
      this.toastr.error('Registration Failed! Please try again')
    });

    console.log('submit working')
    this.frmSignup.reset();
  }


      }
      
     
  
  






