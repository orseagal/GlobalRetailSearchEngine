import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Item } from '../models/item';
import { DataStorageService } from '../shared/data-storage.service';

@Component({
  selector: 'app-kijiji',
  templateUrl: './kijiji.component.html',
  styleUrls: ['./kijiji.component.css']
})
export class KijijiComponent implements OnInit {
  form: FormGroup;
  error:string;
  items:Item[]=null;
  isLoading=false;
  p:any=null;
  resNum:number=20;
  complete:boolean = false;
  
  locations = [
    {name: 'Italy', value: 'Kijiji-Italy'},
    
  ];

    constructor(private formBuilder: FormBuilder,private dataStorageService: DataStorageService, private router:Router) { 
      this.form = this.formBuilder.group({
      keyword: new FormControl(null, [Validators.required]),
      location: new FormControl('Kijiji-Italy'),
          priceLow: new FormControl(null),
           priceHigh: new FormControl(null,),
           resultsNum: new FormControl(this.resNum),
      } 
      
      );
      
    }
    ngOnInit(): void {
    }
  
    clearSearch(){
   window.location.reload();
    }
  onSubmit(){
    console.log(this.form.value);
    console.log("onSubmit");
  
  
  this.resNum = this.form.value.resultsNum;
  this.kijijiItalySearch(this.form.value.keyword);
  this.form.reset();
  
  }
  
  
  kijijiItalySearch(keyword: string){
    this.isLoading=true;
    this.dataStorageService.kijijiItalySearch(keyword).subscribe(result=>{
      this.items = result;
      console.log(this.items);
      this.isLoading=false;
      this.complete = true;
      });
  }
  

  }
  
   
  
  


