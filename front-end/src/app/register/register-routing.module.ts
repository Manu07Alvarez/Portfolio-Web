import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register.component';
import { EmailValidationComponent } from './email-validation/email-validation.component';
import { EmailValidationModule } from './email-validation/email-validation.module';

const routes: Routes = [{ path: '', component: RegisterComponent},
  {path: 'emailValidation', component: EmailValidationComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    EmailValidationModule
  ],
  exports: [RouterModule]
})
export class RegisterRoutingModule { }
