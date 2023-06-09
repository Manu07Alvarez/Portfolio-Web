import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { LoginService } from './service/login.service';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss',]
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage = "";
  showError = false;

  constructor (private fb: FormBuilder, private loginService: LoginService, private router: Router, private modalService: NgbModal) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required,]]
    });
  }

  openModal() {
    const modalRef = this.modalService.open(ResetPasswordComponent);
    // Puedes pasar cualquier dato necesario al componente del modal utilizando modalRef.componentInstance
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const datosUser = {
        email: this.loginForm.value.email,
        password: this.loginForm.value.password
      };

      this.loginService.login(datosUser).subscribe({
        next: (response: any) => {
          console.log('Sesion iniciada correctamente');
        },
        error: (error: any) => {
          if (error.status === 403) {
            // Usuario deshabilitado, redirigir a la página de verificación de correo
            this.router.navigate(['/emailValidation'], { queryParams: { email: datosUser.email } });
          } else {
            console.log('Error al iniciar sesion');
            this.errorMessage = 'La contraseña o el email son incorrectos';
            this.showError = true;
          }
        }
      });
    };
  };
}
