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

  public pruu: Pruu = new Pruu;
  public idUsuario: number;
  public foto: File | null = null;

  constructor(private pruuService: PruuService, private router: Router) {}

  ngOnInit(): void {

  }

public salvarNovoPruu() {
  this.pruuService.novoPruu(this.pruu).subscribe({
    next: (resultado) => {
      this.pruu = resultado;
      Swal.fire('Pruu postado com sucesso!').then((resultado) => {
        if (resultado.isConfirmed) {
          this.pruu = new Pruu();
        }
        if (this.foto) {
          this.uploadImagem(resultado.id); // Faz o upload da imagem
        } else {
         // this.voltar(); // Caso não haja imagem, retornamos
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

uploadImagem(pruuId: string): void {
  const formData = new FormData();
  formData.append('imagem', this.foto!, this.foto!.name);

  this.pruuService.salvarFotoPruu(pruuId, formData).subscribe({
    next: () => {
      Swal.fire('Imagem carregada com sucesso!', '', 'success');
      //this.voltar();
    },
    error: (erro) => {
      Swal.fire('Erro ao fazer upload da imagem: ' + erro.error, 'error');
    }
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
