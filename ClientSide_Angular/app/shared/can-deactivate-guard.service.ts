import { Observable } from 'rxjs';
import { CanDeactivate } from '@angular/router';
import { Injectable } from '@angular/core';


export interface canComponentDeactivate{

canDeactivate: () => Observable<boolean> | Promise<boolean> | boolean

}
@Injectable({providedIn:'root'})
export class CanDeactivateGuard implements CanDeactivate<canComponentDeactivate>{

    canDeactivate(component: canComponentDeactivate,
                  currentRoute: import("@angular/router").ActivatedRouteSnapshot,
                  currentState: import("@angular/router").RouterStateSnapshot,
                  nextState?: import("@angular/router").RouterStateSnapshot): boolean | import("@angular/router").UrlTree | Observable<boolean | import("@angular/router").UrlTree> | Promise<boolean | import("@angular/router").UrlTree> {           
        // throw new Error("Method not implemented.");

        return component.canDeactivate();
    }



}