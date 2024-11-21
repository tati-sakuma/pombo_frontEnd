import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PruuListagemComponent } from './pruu-listagem/pruu-listagem.component';
import { PruuDetalheComponent } from './pruu-detalhe/pruu-detalhe.component';

const routes: Routes = [
  {path: '', component: PruuListagemComponent},
  {path: 'detalhe', component: PruuDetalheComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PruuRoutingModule { }
