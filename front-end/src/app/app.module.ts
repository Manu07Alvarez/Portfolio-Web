import { CvModule } from './cv/cv.module';
import { LoginModule } from './login/login.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { EmailValidationModule } from './register/email-validation/email-validation.module';

@NgModule({
  declarations: [
    AppComponent,
   ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    EmailValidationModule,
    LoginModule,
    CvModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
