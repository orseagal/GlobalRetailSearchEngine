import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Customer } from '../models/customer';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PasswordBeans } from '../models/passwordBean';
import { UserLogin } from '../models/userLogin';
import { Ebay } from "../models/eBay";
import { Mercadolibre } from "../models/mercadolibre";


@Injectable({
    providedIn:'root'
})
export class DataStorageService{

private AWSaddress:string="https://api.couponsystemproject.com/couponSystem/api/";
private serverAPI: string ="http://localhost:8080/websiteSearch/api/search/";
//private AWSaddress:string="http://localhost:8080/couponSystem/api/";
constructor(private http: HttpClient){}



// ----Customers---- //
fetchCustomers(){
    return this.http.get<Customer[]>(this.AWSaddress +'admin/allCustomers', )
}


deleteCustomer(userId:number){
   // const headers=new HttpHeaders({Authorization:'Basic '+btoa("admin:test123")})
return this.http.delete(this.AWSaddress + 'admin/' + userId, )
.pipe(catchError(this.handleError))
}

updateCustomerPassword(password:string, newPassword:string, id:number, clientType:string){
    const passwordBean:PasswordBeans={id ,clientType ,password, newPassword}
    console.log(passwordBean)
    return this.http.put(this.AWSaddress +'customers/update-password' , passwordBean, {responseType: 'json'} )
    .pipe(catchError(this.handleError))
}


ebaySearch(ebay: Ebay){

    return this.http.post<any>(this.serverAPI + "ebaySearchTest" , ebay);
  }

  ebaytest(ebay: Ebay):Observable<any>{
    return this.http.post(this.serverAPI + "test",ebay);
  }

  mercadolibreSearch(mercadolibre:Mercadolibre){
    return this.http.post<any>(this.serverAPI + "mercadolibreSearchTest", mercadolibre )
  }

  kijijiItalySearch(keyword: string){
    return this.http.get<any>(this.serverAPI + "kijijiItaly/" + keyword)

  }




private handleError(errorRes: HttpErrorResponse){
    console.log("handle error");
let errorMessage = 'an unknown error occurred!';
if(!errorRes.error.message || !errorRes.error){
    return throwError(errorMessage);
}

switch(errorRes.error.message){
    case 'INVALID_USERNAME_OR_PASSWORD':
        errorMessage ='Invalid username or password';
        break;
    case 'EMAIL_EXIST':
        errorMessage = 'The email you inserted already exist, please choose a different one'; 
        break;
    case 'NAME_EXIST':
        errorMessage = 'The name you inserted already exist, please choose a different one';
        break;
    case 'PASSWORD_NOT_MATCHED':
        errorMessage = 'Sorry but the password you inserted doesnt match your current password';
        break;
    case 'TITLE_EXIST':
        errorMessage = 'Title is already exist - please choose a different one';
        break;
    case 'the id you insert not exists':
        errorMessage = 'The id you insert not exists';
        break;
       
}       
return throwError(errorMessage);
}
 
}