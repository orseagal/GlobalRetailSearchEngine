export class Ebay{
    constructor(
        public id?:number,
        public keywords?:string,
        public location?:string,
        public category?:string,
        public priceRange_min?:number,
        public priceRange_max?:number,
        public condition?:string,
        public buyingFormat?:string,
        public returns?:string,
        public ExpeditedShippingType?:boolean,
        public FreeShippingOnly?:boolean,
        public handlingTime?:number,
        public sortBy?:string,
        public conversionTo?: string

        ){ 
    }}