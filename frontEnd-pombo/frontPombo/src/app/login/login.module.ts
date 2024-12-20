import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login/login.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { FormsModule } from '@angular/forms';
import { HomeModule } from '../home/home.module';


@NgModule({
  declarations: [
    LoginComponent,
    CadastroComponent,
  ],
  imports: [
    CommonModule,
    LoginRoutingModule,
    FormsModule,
    HomeModule
  ]
})
export class LoginModule { }
