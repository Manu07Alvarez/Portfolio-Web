import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmailValidationComponent } from './email-validation.component';
import { LoginComponent } from 'src/app/login/login.component';


const routes: Routes = [{ path: '', component: EmailValidationComponent },
{ path: 'login', component: LoginComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmailValidationRoutingModule { }
