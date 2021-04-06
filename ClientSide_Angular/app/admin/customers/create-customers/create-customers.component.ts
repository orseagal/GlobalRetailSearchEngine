import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { Router } from '@angular/router';
import { NgForm, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { UserLogin } from 'src/app/models/userLogin';
import { CustomValidators } from 'src/app/shared/custom-validators';
import { DialogOverviewDialog } from 'src/app/shared/mat-dialog/mat-dialog';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-create-customers',
  templateUrl: './create-customers.component.html',
  styleUrls: ['./create-customers.component.css']
})
export class CreateCustomersComponent implements OnInit {
  ngOnInit(){

  }
  public frmSignup: FormGroup;
  isLoading=false;
  error:string=null;
  isRememberMe=false;
  userType= "CUSTOMER";

  constructor(private fb: FormBuilder, private authService: AuthService,private router:Router,public dialog: MatDialog) {
    this.frmSignup = this.createSignupForm();
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogOverviewDialog, {
      width: '480px',
      data: {name: 'Customer has been successfully created!'}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
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

  submit() {
    // do signup or something

    console.log(this.frmSignup.value);

    if(!this.frmSignup.valid){
      return;
    }
    var email:string= this.frmSignup.value.email;
    email= email.trim();
    var password:string = this.frmSignup.value.password;
    password=password.trim();
    var name:string = this.frmSignup.value.name;
    name = name.trim();
    let authObs: Observable<UserLogin>
    this.isLoading=true;
    authObs= this.authService.adminSignUpCustomer(name,password,email,this.userType);

    authObs.subscribe(resData=>{
      console.log(resData)
      this.openDialog();
      this.isLoading=false;
    },errorMessage=>{
      console.log(errorMessage);
      this.error=errorMessage;
      this.isLoading=false;
    });

    console.log('submit working')
    this.frmSignup.reset();
  }

    }