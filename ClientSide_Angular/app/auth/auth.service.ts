import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Router } from "@angular/router";
import { Injectable } from "@angular/core";
import { UserLogin } from "../models/userLogin";
import { catchError, map, tap } from "rxjs/operators";
import { throwError, BehaviorSubject, Observable } from "rxjs";
import { SignupRequestPayload } from "../models/signupRequestpayload";
import { LoginRequestPayload } from "../models/LoginRequestPayload";
import { LoginResponse } from "../models/LoginResponsePayload";
import { LocalStorageService } from "ngx-webstorage";


@Injectable({providedIn:'root'})

export class AuthService{

private ApiAddress:string="http://localhost:8080/websiteSearch/api/";
user = new BehaviorSubject<UserLogin>({username:'guest',authority:'ROLE_GUEST'});
constructor(private http:HttpClient,private router:Router, private localStorage: LocalStorageService){}

refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    username: this.getUserName()
  }
// login(username:string, password:string,isRememberMe:boolean){
//     const headers=new HttpHeaders({Authorization:'Basic '+btoa(username+":"+password)})
// return this.http.get<UserLogin>('http://localhost:8080/couponSystem/api/auth/validateLogin', {headers,withCredentials:true} 
// ).pipe(catchError(this.handleError),tap(resData=>{
//     this.handleAuthentication(resData.id,resData.username,password,resData.email,resData.authority,isRememberMe)
//     console.log(resData)
// }))

// }

login(loginRequestPayload:LoginRequestPayload): Observable<boolean>{
return this.http.post<LoginResponse>(this.ApiAddress+'auth/login' , loginRequestPayload 
).pipe(catchError(this.handleError),map(resData=>{
    this.handleAuthentication(resData);
    return true;
}))
}

autoLogin(){

    const userLogin:UserLogin=JSON.parse(localStorage.getItem('userData'))
    if(!userLogin){
        return;
    }

    const loadedUser = new UserLogin(
        userLogin.id,
        userLogin.username,
        userLogin.password,
        userLogin.email,
        userLogin.authority
    );

    if(loadedUser){
        this.user.next(loadedUser)
    }

}


signUp(signupRequestPayload:SignupRequestPayload):Observable<any>{

    var isRememberMe=false
return this.http.post(this.ApiAddress+'auth/signup', signupRequestPayload , {responseType:'text'}
).pipe(catchError(this.handleError),tap(resData=>{
    //this.login(username,password,isRememberMe)
    //this.handleAuthentication(resData,userLogin.username,password,userLogin.email,userLogin.authority,isRememberMe)
    console.log(resData)
}))

}




adminSignUpCompany(username:string, password:string , email:string, authority:string){

    const userLogin:UserLogin={username, password , email, authority};

return this.http.post<UserLogin>(this.ApiAddress+'admin/createCompany', userLogin , {withCredentials:true}
).pipe(catchError(this.handleError),tap(resData=>{
    console.log(resData)
}))

}
adminSignUpCustomer(username:string, password:string , email:string, authority:string){
const userLogin:UserLogin={username, password , email, authority};
return this.http.post<UserLogin>(this.ApiAddress+'general/createCustomer', userLogin , {withCredentials:true}
).pipe(catchError(this.handleError),tap(resData=>{
    console.log(resData)
}))

}
logout() {
    this.http.post(this.ApiAddress+'auth/logout', this.refreshTokenPayload,
      { responseType: 'text' })
      .subscribe(data => {
        console.log(data);
      }, error => {
        throwError(error);
      })
    this.user.next({username:'guest',authority:'ROLE_GUEST',isAuthenticated:false});
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresAt');
    this.router.navigate(['/login']);
  }

  refreshToken() {
    return this.http.post<LoginResponse>(this.ApiAddress+'auth/refresh/token', this.refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.clear('authenticationToken');
        this.localStorage.clear('expiresAt');
        console.log("refreshToken");
        this.localStorage.store('authenticationToken',
          response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }
  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }
// logOut(){
//     this.user.next({username:'guest',authority:'ROLE_GUEST'});
//     this.router.navigate(['/login']);
//     localStorage.removeItem('userData');
//     localStorage.removeItem('basicauth');
//     sessionStorage.removeItem('userData');
//     sessionStorage.removeItem('basicauth');
    
//      return this.http.get(this.ApiAddress+'auth/logout' ,{withCredentials:true})
//      .subscribe(res=>{
//          console.log(res) 
        
//      })

// }


// private handleAuthentication(id: number,username:string ,password:string,email:string, authority: string,isRememberMe:boolean){
//    const user:UserLogin={id,username,email,authority}
//    let authString = 'Basic '+btoa(username+":"+password);
// if(isRememberMe){
//     localStorage.setItem('userData' , JSON.stringify(user));
//     localStorage.setItem('basicauth', authString);
// }else{
//     sessionStorage.setItem('userData' , JSON.stringify(user));
//     sessionStorage.setItem('basicauth', authString);
// }

// this.user.next(user);


// }

private handleAuthentication(resData:LoginResponse){
    const user:UserLogin={username:resData.username,authority:"ROLE_USER",isAuthenticated:true};
    this.user.next(user);
    this.localStorage.store('authenticationToken', resData.authenticationToken);
    this.localStorage.store('username', resData.username);
    this.localStorage.store('refreshToken', resData.refreshToken);
    this.localStorage.store('expiresAt', resData.expiresAt);
     console.log(resData)
 }
 

 
 

private handleError(errorRes: HttpErrorResponse){
    console.log("handle error");
    //const error = JSON.parse(errorRes.error);
    let errorMessage = 'an unknown error occurred!';
    var errorMessageResponse='';

    if (errorRes.error.message) {
        // client-side error
        errorMessageResponse = errorRes.error.message;
    } else {
        // server-side error
         const error = JSON.parse(errorRes.error);
         errorMessageResponse =error.message;

    }

    console.log(errorRes);
    console.log(errorMessageResponse);
if(errorMessageResponse==''){
    return throwError(errorMessage);
}

switch(errorMessageResponse){
    case 'INVALID_USERNAME_OR_PASSWORD':
        errorMessage ='Invalid username or password';
        break;
    case 'EMAIL_EXIST':
        errorMessage = 'The email you inserted already exist, please choose a different one'; 
        break;
    case 'NAME_EXIST':
        errorMessage = 'The name you inserted already exist, please choose a different one'
    case 'User is disabled':
      errorMessage = 'Your user must be activated first - please check your email'
}
return throwError(errorMessage);
}

routeClient(){
     var userRes: UserLogin;
    this.user.subscribe(user=>{
        userRes= user;
    })
    
switch(userRes.authority){

case 'ROLE_ADMIN':
    this.router.navigate(['/admin'])
    break;
case 'ROLE_COMPANY':
    this.router.navigate(['/company'])
    break;
case 'ROLE_CUSTOMER':
    this.router.navigate(['/products'])        
    break;
}

}

}


