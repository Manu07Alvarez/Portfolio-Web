import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, map } from 'rxjs';
@Injectable({
  providedIn: 'root'
})

export class EmailService {

  private sendMail = 'http://localhost:8080/sendVerificationCode';
  private verifyCode = 'http://localhost:8080/verify'

  constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) { }

  verificarCodigo(email: string, code: string): Observable<any> {
    const url = `${this.verifyCode}?email=${email}&code=${code}`;
    return this.http.get(url, { responseType: 'text' }).pipe(
      map(response => {
        console.log('codigo de verificacion correcto');
        this.router.navigate(['/login']);
        return response;
      }),
      catchError(error => {
        console.error('Error al verificar el codigo', error);
        throw new Error('Something went wrong');
      })
    );
  }
  enviarEmail(email : string): Observable<any> {
    const url = `${this.sendMail}?email=${email}`;
    return this.http.get(url).pipe(
      map(response => {
        console.log('El correo electrónico de verificación ha sido enviado');
        return response;

      }),
      catchError(error => {
        console.error('Error al enviar el correo electrónico de verificación:', error);
        throw new Error('Something went wrong');
      })
    );
  }
}
