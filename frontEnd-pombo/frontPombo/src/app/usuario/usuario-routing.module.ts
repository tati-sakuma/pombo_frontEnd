import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminInicioComponent } from './admin-inicio/admin-inicio.component';
import { AdminListaDenunciaComponent } from './admin-lista-denuncia/admin-lista-denuncia.component';
import { AdminDetalheDenunciaComponent } from './admin-detalhe-denuncia/admin-detalhe-denuncia.component';
import { UsuarioDetalheComponent } from './usuario-detalhe/usuario-detalhe.component';

const routes: Routes = [
  { path: 'admin', component: AdminInicioComponent },
  { path: 'admin/lista-denuncia', component: AdminListaDenunciaComponent },
  { path: 'admin/detalhe-denuncia/:id', component: AdminDetalheDenunciaComponent },
  { path: 'detalhe', component: UsuarioDetalheComponent },
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
