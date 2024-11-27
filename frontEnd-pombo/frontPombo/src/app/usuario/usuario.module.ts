import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { UsuarioListagemComponent } from './usuario-listagem/usuario-listagem.component';
import { UsuarioDetalheComponent } from './usuario-detalhe/usuario-detalhe.component';
import { FormsModule } from '@angular/forms';
import { HomeModule } from '../home/home.module';
import { RouterModule } from '@angular/router';
import { HomeComponent } from '../home/home/home.component';
import { SharedModule } from '../shared/shared.module';
import { PruuRoutingModule } from '../pruu/pruu-routing.module';
import { AdminInicioComponent } from './admin-inicio/admin-inicio.component';
import { AdminDetalheDenunciaComponent } from './admin-detalhe-denuncia/admin-detalhe-denuncia.component';
import { AdminListaDenunciaComponent } from './admin-lista-denuncia/admin-lista-denuncia.component';
import { AdminGerenciarUsuariosComponent } from './admin-gerenciar-usuarios/admin-gerenciar-usuarios.component';

@NgModule({
  declarations: [
    UsuarioListagemComponent,
    UsuarioDetalheComponent,
    AdminDetalheDenunciaComponent,
    AdminListaDenunciaComponent,
    AdminGerenciarUsuariosComponent,
    AdminInicioComponent
  ],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    FormsModule,
    HomeModule,
    PruuRoutingModule,
    RouterModule
  ]
})
export class UsuarioModule { }
