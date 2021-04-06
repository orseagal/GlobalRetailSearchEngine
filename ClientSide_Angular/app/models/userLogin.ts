
export class UserLogin{

   public constructor(
    public id?: number,
    public username?: string,
    public password?: string,
    public email?: string,
    public authority?: string,
    public isAuthenticated?: boolean
   ){}
}