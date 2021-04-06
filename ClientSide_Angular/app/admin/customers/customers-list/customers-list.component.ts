import { Component, OnInit } from '@angular/core';
import { Customer } from 'src/app/models/customer';
import { DataStorageService } from 'src/app/shared/data-storage.service';
import { UserLogin } from 'src/app/models/userLogin';

@Component({
  selector: 'app-customers-list',
  templateUrl: './customers-list.component.html',
  styleUrls: ['./customers-list.component.css']
})
export class CustomersListComponent implements OnInit {
isLoading:boolean=true;
customers:Customer[]=null;
message:string;
error:string=null;

constructor(private dataStorageService: DataStorageService ) { }
 
   ngOnInit() {
     this.fetchCustomers();
   }
 

 fetchCustomers(){
  this.dataStorageService.fetchCustomers().subscribe(customers=>{
  this.customers= customers;
  this.isLoading=false;

   },errorMessage=>{
    this.error=errorMessage;
    console.log(errorMessage);
  });
 }
}
