import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PruuRoutingModule } from './pruu-routing.module';
import { PruuDetalheComponent } from './pruu-detalhe/pruu-detalhe.component';
import { PruuListagemComponent } from './pruu-listagem/pruu-listagem.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { HomeModule } from '../home/home.module';


@NgModule({
  declarations: [
    PruuDetalheComponent,
    PruuListagemComponent
  ],
  imports: [
    CommonModule,
    PruuRoutingModule,
    FormsModule,
    SharedModule,
    HomeModule
  ]
})
export class PruuModule { }
