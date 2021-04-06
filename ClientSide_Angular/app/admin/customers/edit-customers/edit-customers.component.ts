import { Component, OnInit, TemplateRef } from '@angular/core';
import { Customer } from 'src/app/models/customer';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { DataStorageService } from 'src/app/shared/data-storage.service';

@Component({
  selector: 'app-edit-customers',
  templateUrl: './edit-customers.component.html',
  styleUrls: ['./edit-customers.component.css']
})
export class EditCustomersComponent implements OnInit {
  customers:Customer[]=[];
  modalRef: BsModalRef;
  message: string;
   constructor(private dataStorageService: DataStorageService ,private modalService: BsModalService) { }
 
   ngOnInit() {
     this.fetchCustomers();
   }
 
   openModal(template: TemplateRef<any>) {
     this.modalRef = this.modalService.show(template, {class: 'modal-sm'});
   }
  
   confirm(customer:Customer): void {
     this.onDelete(customer);
     this.modalRef.hide();
   }
  
   decline(): void {
     this.modalRef.hide();
   }
 
 onDelete(customer:Customer){
   // var isDelete = confirm("Are You Sure You Want To Delete Customer " +customer.name +" ?");
   
     console.log("delete")
     this.dataStorageService.deleteCustomer(customer.id).subscribe(result=>{
       this.fetchCustomers();
       console.log("customer deleted")
     })
   
 }
 
 
 fetchCustomers(){
   this.dataStorageService.fetchCustomers().subscribe(customers=>{
     this.customers= customers;
   });
    
 }
}
