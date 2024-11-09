import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuarioListagemComponent } from './usuario-listagem/usuario-listagem.component';

const routes: Routes = [
  {path: "", component: UsuarioListagemComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
