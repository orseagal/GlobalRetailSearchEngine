import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArray, FormBuilder, FormControl, Validators, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { Ebay } from '../models/eBay';
import { Item } from '../models/item';
import { Mercadolibre } from '../models/mercadolibre';
import { DataStorageService } from '../shared/data-storage.service';


@Component({
  selector: 'app-mercadolibre',
  templateUrl: './mercadolibre.component.html',
  styleUrls: ['./mercadolibre.component.css']
})
export class MercadolibreComponent implements OnInit {

  form: FormGroup;

conditionData=[];
buyFormatData=[];
error:string;
items:Item[]=null;
isLoading=false;
p:any=null;
resNum:number=20;
complete:boolean = false;

locations = [
  {name: 'All', value: 'Mercadolibre-GLOBAL'},
  {name: 'Argentina', value: 'MLA'},
  {name: 'Mexico', value: 'MLM'},
  {name: 'Paraguay', value: 'MPY'}, 
  {name: 'Costa Rica', value: 'MCR'},
  {name: 'Bolivia', value: 'MBO'},
  {name: 'Cuba', value: 'MCU'},
  {name: 'Chile', value: 'MLC'},
  {name: 'El Salvador', value: 'MSV'},
  {name: 'Dominicana', value: 'MRD'},
  {name: 'Venezuela', value: 'MLV'},
  {name: 'Brasil', value: 'MLB'},
  {name: 'Ecuador', value: 'MEC'},
  {name: 'Uruguay', value: 'MLU'},
  {name: 'Honduras', value: 'MHN'},
  {name: 'Perú', value: 'MPE'},
  {name: 'Panamá', value: 'MPA'},
  {name: 'Nicaragua', value: 'MNI'},
  {name: 'Guatemala', value: 'MGT'},
  {name: 'Colombia', value: 'MCO'}
  
];

  constructor(private formBuilder: FormBuilder,private dataStorageService: DataStorageService, private router:Router) { 
    this.form = this.formBuilder.group({
      condition: new FormArray([],[minSelectedCheckboxes(1)]),
    keyword: new FormControl(null, [Validators.required]),
    location: new FormControl(null,Validators.required),
        priceLow: new FormControl(null),
         priceHigh: new FormControl(null,),
         resultsNum: new FormControl(this.resNum),


    } 
    
    , {validator: priceValidator('priceLow', 'priceHigh')}
    
    );
    
  }



  ngOnInit(): void {
    console.log("test");
  }

  clearSearch(){
 window.location.reload();
  }
onSubmit(){
  console.log(this.form.value);
  console.log("onSubmit");

 let mercadolibre = new Mercadolibre;
// ebay.keywords = this.form.value.keyword;
// ebay.handlingTime = this.form.value.handlingTime;
// ebay.location = this.form.value.location;
// ebay.priceRange_max = this.form.value.priceHigh;
// ebay.priceRange_min = this.form.value.priceLow;
// ebay.returns = this.form.value.returns;
// ebay.ExpeditedShippingType = this.form.value.ExpeditedShippingType;
// ebay.FreeShippingOnly = this.form.value.FreeShippingOnly;
// ebay.sortBy = this.form.value.SortSelection;
// this.resNum = this.form.value.ResultsNum;
//console.log(ebay);
this.resNum = this.form.value.resultsNum;
mercadolibre.keywords = this.form.value.keyword;
mercadolibre.location =  this.form.value.location;
this.mercadolibreSearch(mercadolibre);
this.form.reset();

}


mercadolibreSearch(mercadolibre: Mercadolibre){
  console.log(mercadolibre);
  this.isLoading=true;
  this.dataStorageService.mercadolibreSearch(mercadolibre).subscribe(result=>{
    this.items = result;
    console.log(this.items);
    this.isLoading=false;
    this.complete = true;
    });
   

}




}

function minSelectedCheckboxes(max = 1) {
  const validator: ValidatorFn = (formArray: FormArray) => {
    const totalSelected = formArray.controls
      .map(control => control.value)
      .reduce((prev, next) => next ? prev + next : prev, 0);

    return totalSelected <= max ? null : { required: true };
  };

  return validator;
}

function priceRange() {


  
  const validator: ValidatorFn = (formArray: FormGroup) => {
   // const totalSelected = formArray.get('priceLow').value  
  console.log(formArray.value);
    return formArray;
  };
  return validator;

}


export function priceValidator(targetKey: string, toMatchKey: string): ValidatorFn {
  //return (group: FormGroup): {[key: string]: any} => {
    const validator: ValidatorFn = (group: FormGroup) => {
    const target = group.controls[targetKey];
    const toMatch = group.controls[toMatchKey];
    return target.value > toMatch.value ? { priceValidator: true } :null ;
 // };
};

return validator;
}
