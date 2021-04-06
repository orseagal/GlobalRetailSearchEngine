import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { Ebay } from '../models/eBay';
import { Item } from '../models/item';
import { DataStorageService } from '../shared/data-storage.service';


@Component({
  selector: 'app-ebay',
  templateUrl: './ebay.component.html',
  styleUrls: ['./ebay.component.css']
})
export class EbayComponent implements OnInit {
  form: FormGroup;
  //condition: Array<String> =['New','Used','Very good','Good' ];
conditionData=[];
buyFormatData=[];
error:string;
items:Item[]=null;
isLoading=false;
p:any=null;
resNum:number=20;
complete:boolean = false;

locations = [
  {name: 'All', value: 'EBAY-GLOBAL'},
  {name: 'eBay United States', value: 'EBAY-US'},
  {name: 'eBay Austria', value: 'EBAY-AT'},
  {name: 'eBay Australia', value: 'EBAY-AU'},
  {name: 'eBay Switzerland', value: 'EBAY-CH'},
  {name: 'eBay Germany', value: 'EBAY-DE'},
  {name: 'eBay Canada (English)', value: 'EBAY-ENCA'},
  {name: 'eBay Spain', value: 'EBAY-ES'},
  {name: 'eBay France', value: 'EBAY-FR'},
  {name: 'eBay Belgium (French)', value: 'EBAY-FRBE'},
  {name: 'eBay Canada (French)', value: 'EBAY-FRCA'},
  {name: 'eBay UK', value: 'EBAY-GB'},
  {name: 'eBay Hong Kong', value: 'EBAY-HK'},
  {name: 'eBay Ireland', value: 'EBAY-IE'},
  {name: 'eBay India', value: 'EBAY-IN'},
  {name: 'eBay Italy', value: 'EBAY-IT'},
  {name: 'eBay Malaysia', value: 'EBAY-MY'},
  {name: 'eBay Netherlands', value: 'EBAY-NL'},
  {name: 'eBay Belgium (Dutch)', value: 'EBAY-NLBE'},
  {name: 'eBay Philippines', value: 'EBAY-PH'},
  {name: 'eBay Poland', value: 'EBAY-PL'},
  {name: 'eBay Singapore', value: 'EBAY-SG'},
];

categories = [
  {name: 'All', value: 'All'},
  {name: 'Antiques', value: '20081'},
  {name: 'Art', value: '550'},
  {name: 'Baby', value: '2984'},
  {name: 'Books', value: '267'},
  {name: 'Business & Industrial', value: '12576'},
  {name: 'Cameras & Photo', value: '625'},
  {name: 'Cell Phones & Accessories', value: '15032'},
  {name: 'Clothing, Shoes & Accessories', value: '11450'},
  {name: 'Coins & Paper Money', value: '11116'},
  {name: 'Collectibles', value: '1'},
  {name: 'Computers/Tablets & Networking', value: '58058'},
  {name: 'Consumer Electronics', value: '293'},
  {name: 'Crafts', value: '14339'},
  {name: 'Dolls & Bears', value: '237'},
  {name: 'DVDs & Movies', value: '11232'},
  {name: 'Entertainment Memorabilia', value: '45100'},
  {name: 'Gift Cards & Coupons', value: '172008'},
  {name: 'Health & Beauty', value: '26395'},
  {name: 'Home & Garden', value: '11700'},
  {name: 'Jewelry & Watches', value: '281'},
  {name: 'Music', value: '11233'},

  {name: 'Musical Instruments & Gear', value: '619'},
  {name: 'Pet Supplies', value: '1281'},
  {name: 'Pottery & Glass', value: '870'},
  {name: 'Real Estate', value: '10542'},
  {name: 'Specialty Services', value: '316'},
  {name: 'Sporting Goods', value: '888'},
  {name: 'Sports Mem, Cards & Fan Shop', value: '64482'},
  {name: 'Stamps', value: '260'},
  {name: 'Tickets & Experiences', value: '1305'},
  {name: 'Toys & Hobbies', value: '220'},
  {name: 'Travel', value: '3252'},
  {name: 'Video Games & Consoles', value: '1249'},
];

 get conditionFormArray() {
  return this.form.controls.condition as FormArray;
}

get buyFormatFormArray() {
  return this.form.controls.buyFormat as FormArray;
}
  constructor(private formBuilder: FormBuilder,private dataStorageService: DataStorageService, private router:Router) { 
    this.form = this.formBuilder.group({
      condition: new FormArray([],[minSelectedCheckboxes(1)]),
    keyword: new FormControl(null, [Validators.required]),
    location: new FormControl('EBAY-GLOBAL',Validators.required),
    category: new FormControl("All",),
        priceLow: new FormControl(null),
         priceHigh: new FormControl(null,),
         buyFormat: new FormArray([],[minSelectedCheckboxes(1)]),
         returns: new FormControl(),
         SortSelection: new FormControl("BestMatch"),
         ResultsNum: new FormControl(this.resNum),
         FreeShippingOnly: new FormControl(),
         ExpeditedShippingType: new FormControl(),
         handlingTime: new FormControl(null, [Validators.max(30),Validators.min(1)]),

    } 
    
    , {validator: priceValidator('priceLow', 'priceHigh')}
    
    );

    of(this.getConditions()).subscribe(condition => {
      this.conditionData = condition;
      this.addCheckboxes();
    }); 

    of(this.getBuyFormat()).subscribe(buyFormat => {
      this.buyFormatData = buyFormat;
      this.addCheckboxes2();
    }); 
    
  }

  private addCheckboxes() {
    this.conditionData.forEach(() => this.conditionFormArray.push(new FormControl(false)));
  }

  private addCheckboxes2() {
    this.buyFormatData.forEach(() => this.buyFormatFormArray.push(new FormControl(false)));
  }
  getBuyFormat() {
    return [
       'FixedPrice', 'Auction' , 'Classified Ad'
    ];
  }

  getConditions() {
    return [
       'new', 'good' , 'used', 'very good' , 'accaptable'
    ];
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

  const selectedConditionIds = this.form.value.condition
  .map((checked, i) => checked ? this.conditionData[i] : null)
  .filter(v => v !== null);
const selectedBuyFormatIds = this.form.value.buyFormat
  .map((checked, i) => checked ? this.buyFormatData[i] : null)
  .filter(v => v !== null);

let ebay = new Ebay;
ebay.keywords = this.form.value.keyword;
ebay.keywords = ebay.keywords.trim();
ebay.buyingFormat = selectedBuyFormatIds[0];
ebay.condition = selectedConditionIds[0];
ebay.handlingTime = this.form.value.handlingTime;
ebay.location = this.form.value.location;
ebay.category = this.form.value.category;
ebay.priceRange_max = this.form.value.priceHigh;
ebay.priceRange_min = this.form.value.priceLow;
ebay.returns = this.form.value.returns;
ebay.ExpeditedShippingType = this.form.value.ExpeditedShippingType;
ebay.FreeShippingOnly = this.form.value.FreeShippingOnly;
ebay.sortBy = this.form.value.SortSelection;
this.resNum = this.form.value.ResultsNum;
ebay.conversionTo = "ILS";
console.log(ebay);

this.form.reset();

this.ebaySearch(ebay);
}


ebaySearch(ebay: Ebay){
  this.isLoading=true;
  this.dataStorageService.ebaySearch(ebay).subscribe(result=>{
    this.items = result;
    console.log(this.items);
    this.isLoading=false;
    this.complete = true;
    });
   

}


// fetchResults(){
  
//   this.ebayService.ebaySearch().subscribe(results=>{

//     for(let resItem of results){
//     let item= new Item; 
//     item.title = resItem['title'];
//     item.price = resItem['price'];
//     item.image = resItem['image'];
//    this.items.push(item);
//     }


//   });
// }


// fetchEbaySearch(){

//   this.ebayService.ebaySearch().subscribe(searchRes=>{
//     //console.log(searchRes)
//     console.log(searchRes['findItemsAdvancedResponse'][0]['searchResult'][0]['item']);
    
    // for(let ebayItem in searchRes['findItemsAdvancedResponse'][0]['searchResult'][0]['item']) {
    //   var item= new Item;
    //    item.title =  ebayItem['title'];
    //    console.log( ebayItem)
    //   // item.price = ebayItem['sellingStatus'][0]['convertedCurrentPrice'][0]['__value__'];
    //    //item.condition = ebayItem['condition'][0]['conditionDisplayName'][0];
    //    console.log(item);
    // }

  //   searchRes['findItemsAdvancedResponse'][0]['searchResult'][0]['item'].forEach(function (ebayItem) { 
  //     let item= new Item; 
  //     //console.log(ebayItem['title'][0]);
  //     item.title=ebayItem['title'][0];
  //     item.image=ebayItem['galleryURL'][0];
  //     item.price = ebayItem['sellingStatus'][0]['convertedCurrentPrice'][0]['__value__'];
  //     item.condition = ebayItem['condition'][0]['conditionDisplayName'][0];
  //     console.log(item);
  //      this.items.push(item);
  // });  
  
//     for(let ebayItem of searchRes['findItemsAdvancedResponse'][0]['searchResult'][0]['item']){
//       let item= new Item; 
//      console.log(ebayItem['title'][0]);
//       item.title=ebayItem['title'][0];
//       item.image=ebayItem['galleryURL'][0];
//       item.price = ebayItem['sellingStatus'][0]['convertedCurrentPrice'][0]['__value__'];
//       //item.condition = ebayItem['condition'][0]['conditionDisplayName'][0];
//       console.log(item);
//       this.items.push(item);
//       //console.log()
//     }

//   });
    
// }

// matchPassword(control: AbstractControl): ValidationErrors | null {
 
//   const password = control.get("priceLow").value;
//   const confirm = control.get("priceHigh").value;
//   console.log(password);
//   console.log(confirm);

//   if (password != confirm) { return { 'noMatch': true } }

//   return null

// }

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

  // const password: string = control.get('password').value; // get password from our password form control
  // const confirmPassword: string = control.get('confirmPassword').value; // get password from our confirmPassword form control
  // // compare is the password math
  // if (password !== confirmPassword) {
  //   // if they don't match, set an error in our confirmPassword form control
  //   control.get('confirmPassword').setErrors({ NoPassswordMatch: true });
  // }
  
  const validator: ValidatorFn = (formArray: FormGroup) => {
   // const totalSelected = formArray.get('priceLow').value  
  console.log(formArray.value);
    return formArray;
  };
  return validator;

}

// export function NoNegativeNumbers(control: FormGroup) {

//   console.log('test');
//   control.controls["priceLow"]
//   console.log( control.controls["priceLow"]);
 
//   return control.value < 0 ? { negativeNumber: true } : null;
// }

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

//control.get('confirmPassword').setErrors({ NoPassswordMatch: true });
// export function equalValueValidator(targetKey: string, toMatchKey: string): ValidatorFn {
//   return (group: FormGroup): {[key: string]: any} => {
//     const target = group.controls[targetKey];
//     const toMatch = group.controls[toMatchKey];
//     if (target.touched && toMatch.touched) {
//       const isMatch = target.value === toMatch.value;
//       // set equal value error on dirty controls
//       if (!isMatch && target.valid && toMatch.valid) {
//         toMatch.setErrors({equalValue: targetKey});
//         const message = targetKey + ' != ' + toMatchKey;
//         return {'equalValue': message};
//       }
//       if (isMatch && toMatch.hasError('equalValue')) {
//         toMatch.setErrors(null);
//       }
//     }

//     return null;
//   };
// }