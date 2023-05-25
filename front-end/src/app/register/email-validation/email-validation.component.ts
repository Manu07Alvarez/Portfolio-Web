
import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { EmailService } from './email.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-email-validation',
  templateUrl: './email-validation.component.html',
  styleUrls: ['./email-validation.component.scss']
})

export class EmailValidationComponent implements OnInit {

  email!: string;
  codigoVerificacion!: string;


  constructor(private cookieService: CookieService, private http: HttpClient, private emailService: EmailService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.email = params['email'];
    });
    console.log(this.email);
    const hasComponentStarted = sessionStorage.getItem('hasComponentStarted');
    if (hasComponentStarted) {
      console.log("El componente ya ha sido iniciado anteriormente.");
    } else {
      this.emailService.enviarEmail(this.email).subscribe();
      console.log("El componente se ha iniciado por primera vez.");
      sessionStorage.setItem('hasComponentStarted', 'true');
    }
  }

  enviarCorreoVerificacion(): void {
    this.emailService.enviarEmail(this.email).subscribe();
  }

  enviarVerificarCodigo(): void {
    this.emailService.verificarCodigo(this.email, this.codigoVerificacion).subscribe();
  }
}
