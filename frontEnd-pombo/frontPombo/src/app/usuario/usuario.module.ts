import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { UsuarioListagemComponent } from './usuario-listagem/usuario-listagem.component';
import { UsuarioDetalheComponent } from './usuario-detalhe/usuario-detalhe.component';
import { FormsModule } from '@angular/forms';
import { HomeModule } from '../home/home.module';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    UsuarioListagemComponent,
    UsuarioDetalheComponent
  ],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    FormsModule,
    HomeModule,
    RouterModule
  ]
})
export class UsuarioModule { }
