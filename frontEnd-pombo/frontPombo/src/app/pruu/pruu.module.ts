import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PruuRoutingModule } from './pruu-routing.module';
import { PruuDetalheComponent } from './pruu-detalhe/pruu-detalhe.component';
import { PruuListagemComponent } from './pruu-listagem/pruu-listagem.component';


@NgModule({
  declarations: [
    PruuDetalheComponent,
    PruuListagemComponent
  ],
  imports: [
    CommonModule,
    PruuRoutingModule
  ]
})
export class PruuModule { }
