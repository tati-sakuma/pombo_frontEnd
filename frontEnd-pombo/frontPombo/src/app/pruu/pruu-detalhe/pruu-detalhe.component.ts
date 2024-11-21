import { Component, OnInit } from '@angular/core';
import { Pruu } from '../../shared/model/pruu';
import { PruuService } from '../../shared/service/pruu.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-pruu-detalhe',
  templateUrl: './pruu-detalhe.component.html',
  styleUrl: './pruu-detalhe.component.sass',
})
export class PruuDetalheComponent implements OnInit {

  public pruu: Pruu;
  public idUsuario: number;

  constructor(private pruuService: PruuService, private router: Router) {}

  ngOnInit(): void {
    let token = localStorage.getItem('tokenUsuarioAutenticado');

    if(token){
      let tokenJSON: any = jwtDecode(token);
      this.idUsuario = tokenJSON.userId;
    }
  }


public salvarNovoPruu() {
  this.pruuService.novoPruu(this.pruu).subscribe({
    next: (resultado) => {
      this.pruu = resultado;
      this.pruu.usuario.id = this.idUsuario;
      Swal.fire('Pruu postado com sucesso!').then((resultado) => {
        if (resultado.isConfirmed) {
          this.pruu = new Pruu();
        }
      });
    },
    error: (erro) => {
      let erroString = this.transformarErroEmString(erro.error);
      Swal.fire({
        icon: 'error',
        title: 'Erro ao cadastrar novo pruu!',
        text: 'Erro ao cadastrar novo pruu:' + erroString,
      });
    },
  });
}

private transformarErroEmString(erro: any): string {
  if (typeof erro === 'object') {
    return Object.entries(erro)
      .map(([key, value]) => `${key}: ${value}`)
      .join(', ');
  }
  return String(erro);
}



}
