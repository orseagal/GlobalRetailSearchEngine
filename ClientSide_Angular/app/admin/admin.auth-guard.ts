import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { map, take } from "rxjs/operators";

@Injectable({providedIn:'root'})
export class AdminAuthGuard implements CanActivate{
    constructor(private authService: AuthService, private router: Router){}
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean>| Promise<boolean>{
    
        return this.authService.user.pipe(take(1),map(user=>{
            if(user!=null){
                const isAuth=user.authority;
                if(isAuth=='ROLE_ADMIN'){
                    return true;
            }
        
            }
            this.router.navigate(['/products'])
        }))
        
         }
}