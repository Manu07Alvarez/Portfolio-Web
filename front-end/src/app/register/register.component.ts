  import { Component } from '@angular/core';
  import { RegisterService } from './service/register.service';
  import { FormBuilder, FormGroup, Validators, FormControl, ValidatorFn, AbstractControl } from '@angular/forms';
  import { Router } from '@angular/router';
  @Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
  })

  export class RegisterComponent {
    registerForm: FormGroup;
    errorMessage = "";
    showError = false;

    constructor(private fb: FormBuilder, private registerService: RegisterService, private router: Router) {
      this.registerForm = this.fb.group({
        firstname: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*')]],
        lastname: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*')]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [
          Validators.required,
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$'),
          (control: FormControl) => {
            const specialChars = control.value ? control.value.replace(/[^!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]+/g, '') : '';
            const maxSpecialCharacters = 1;

            if (specialChars.length > maxSpecialCharacters) {
              return { 'maxSpecialCharacters': true };
            }

            return null;
          }
        ]],
        confirmPassword: ['', Validators.required]
          }, { validator: this.passwordMatchValidator });
      }

      passwordMatchValidator: ValidatorFn = (control: AbstractControl): { [key: string]: any } | null => {
        const password = control.get('password');
        const confirmPassword = control.get('confirmPassword');

        return password && confirmPassword && password.value !== confirmPassword.value ? { 'passwordMismatch': true } : null;
      }

    enviar(): void {
      if (this.registerForm.valid) {
        const datosUser = {
          firstname: this.registerForm.value.firstname,
          lastname: this.registerForm.value.lastname,
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
            this.registerService.crearVerificationCodeInDb({ email: datosUser.email  }).subscribe()
            this.router.navigate(['/emailValidation'], { queryParams: { email: datosUser.email } }).then(() => {
            })
          }
        });
      };
    }
}

