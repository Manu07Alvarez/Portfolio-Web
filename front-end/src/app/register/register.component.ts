  import { Component, OnInit } from '@angular/core';
  import { RegisterService } from './service/register.service';
  import { Observer } from 'rxjs';
  import { FormBuilder, FormGroup, Validators } from '@angular/forms';
  import { Router } from '@angular/router';
  import { CookieService } from 'ngx-cookie-service';
  @Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
  })

  export class RegisterComponent {
    registerForm: FormGroup;
    errorMessage = "";
    showError = false;

    constructor(private fb: FormBuilder, private registerService: RegisterService, private router: Router, private cookieService: CookieService) {
      this.registerForm = this.fb.group({
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]]
      });
    }


    enviar(): void {
      if (this.registerForm.valid) {
        const datosUser = {
          username: this.registerForm.value.username,
          email: this.registerForm.value.email,
          password: this.registerForm.value.password
        };

        this.registerService.crearUser(datosUser).subscribe({
          next: (response: any) => {
            console.log('Error al crear el usuario');
            this.errorMessage = 'El correo electrónico ya está en uso';
            this.showError = true;
          },
          error: (error: any) => {
            this.registerService.crearVerificationCodeDb({ email: datosUser.email  }).subscribe()
            this.router.navigate(['/emailValidation'], { queryParams: { email: datosUser.email } }).then(() => {
            })
          }
        });
      };
    }
}
