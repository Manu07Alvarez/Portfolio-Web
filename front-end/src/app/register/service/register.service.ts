import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable} from 'rxjs';
import { catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private verifiedUrl = 'http://localhost:8080/api/auth/register';
  private verifiedCodeUrl = 'http://localhost:8080/api/auth/verificationCode';

  constructor(private http: HttpClient) { }


  crearUser(datosUser: any){
    return this.http.post(this.verifiedUrl, datosUser).pipe(
    catchError(this.handleError));
  }

  crearVerificationCodeInDb(datosUser : any){
    return this.http.post(this.verifiedCodeUrl, datosUser)
  }



  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return new Observable(observer => {
      observer.error('Something bad happened; please try again later.');
    });
  }
}

