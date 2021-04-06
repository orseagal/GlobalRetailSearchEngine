import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';

@Injectable({
    providedIn:'root'
})
export class BasicAuthHttpInterceptorService implements HttpInterceptor{

    intercept(req:HttpRequest<any>, next: HttpHandler) {
       if(sessionStorage.getItem('basicauth') || localStorage.getItem('basicauth')){
        console.log('interceptor works!')
        console.log(localStorage.getItem('basicauth'))
        req.clone({
            setHeaders:{
                Authorization: sessionStorage.getItem('basicauth')
            }
        })
       }
       return next.handle(req);
    }

constructor(){}
}