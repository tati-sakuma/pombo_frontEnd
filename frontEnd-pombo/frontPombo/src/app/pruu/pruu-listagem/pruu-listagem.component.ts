import { Component, OnInit } from '@angular/core';
import { Pruu } from '../../shared/model/pruu';
import { PruuService } from '../../shared/service/pruu.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { PruuSeletor } from '../../shared/model/seletor/pruu.seletor';

@Component({
  selector: 'app-pruu-listagem',
  templateUrl: './pruu-listagem.component.html',
  styleUrl: './pruu-listagem.component.sass'
})
export class PruuListagemComponent implements OnInit {

  public pruus: Pruu[] = [];
  public pruuSeletor = new PruuSeletor;

  constructor(private pruuService: PruuService,
              private router: Router) {}

  ngOnInit(): void {

  }


  public pesquisar() {
    this.pruuService.listarComFiltros(this.pruuSeletor).subscribe({
      next: (resultado) => {
        this.pruus = resultado;
      },
      error: (erro) => {
        let erroString = this.transformarErroEmString(erro.error);
        Swal.fire({
          title: 'Erro!',
          text: 'Erro ao consultar todos os pruus: ' + erroString,
          icon: 'error',
        });
      }
  });
  }

  public limpar(): void {
    this.pruuSeletor = new PruuSeletor();
  }



// Método para tratar o Json de erro
  private transformarErroEmString(erro: any): string {
    if (typeof erro === 'object') {
      return Object.entries(erro)
        .map(([key, value]) => value)
        .join('; ');
    }
    return String(erro);
  }

}
