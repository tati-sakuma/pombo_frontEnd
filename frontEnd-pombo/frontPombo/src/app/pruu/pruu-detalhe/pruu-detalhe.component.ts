import { Component, OnInit } from '@angular/core';
import { Pruu } from '../../shared/model/pruu';
import { PruuService } from '../../shared/service/pruu.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-pruu-detalhe',
  templateUrl: './pruu-detalhe.component.html',
  styleUrl: './pruu-detalhe.component.sass',
})
export class PruuDetalheComponent implements OnInit {

  public pruu: Pruu;

  constructor(private pruuService: PruuService, private router: Router) {}

  ngOnInit(): void {}


public salvarNovoPruu() {
  this.pruuService.novoPruu(this.pruu).subscribe({
    next: (resultado) => {
      this.pruu = resultado;
      Swal.fire('Pruu postado com sucesso!').then((resultado) => {
        if (resultado.isConfirmed) {
          this.pruu = new Pruu();
        }
      });
    },
    error: (erro) => {
      Swal.fire({
        icon: 'error',
        title: 'Erro ao cadastrar novo pruu!',
        text: 'Erro ao cadastrar novo pruu:' + erro.error,
      });
    },
  });
}
}
