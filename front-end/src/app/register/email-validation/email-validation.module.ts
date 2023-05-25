import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmailValidationRoutingModule } from './email-validation-routing.module';
import { EmailValidationComponent } from './email-validation.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    EmailValidationComponent
  ],
  imports: [
    FormsModule,
    CommonModule,
    EmailValidationRoutingModule
  ]
})
export class EmailValidationModule { }
