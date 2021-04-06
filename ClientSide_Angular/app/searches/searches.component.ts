import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-searches',
  templateUrl: './searches.component.html',
  styleUrls: ['./searches.component.css']
})
export class SearchesComponent implements OnInit {
 image:string='/assets/img/ebay-logo-1-1200x630-margin.png';

  constructor(private router:Router) { }

  ngOnInit(): void {
   
    console.log(this.router.url);
    this.imageChange((this.router.url).slice(10));
  }


  imageChange(website: string){
    switch (website) {
      case 'ebay':
        this.image='/assets/img/ebay-logo-1-1200x630-margin.png'
        this.router.navigate(['searches/ebay']);
        break;
        case 'mercadolibre':
          this.image='/assets/img/mercado-libre-logo.png'
          this.router.navigate(['searches/mercadolibre']);
          break;
          case 'OLX':
            this.image='/assets/img/OLX.jpg'
            break;
            case 'kijiji':
              this.image='/assets/img/Kijiji-Logo.wine.png'
              this.router.navigate(['searches/kijiji']);
              break;
            
      default:
        break;
    }

console.log(website);
  }

}
