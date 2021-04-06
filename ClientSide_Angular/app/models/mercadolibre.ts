export class Mercadolibre{
    constructor(
        public id?:number,
        public keywords?:string,
        public location?:string,
        public category?:string,
        public priceRange_min?:number,
        public priceRange_max?:number,
        public condition?:string,
        public sortBy?:string,
        public conversionTo?: string

        ){ 
    }}