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
  public imagePreview: string | ArrayBuffer | null = null; // Para armazenar o preview da imagem

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

      });

      if (this.foto) {
        this.uploadImagem(resultado.id); // Faz o upload da imagem
      } else {
       this.voltar(); // Caso não haja imagem, retornamos
      }
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

public uploadImagem(pruuId: string): void {
  const formData = new FormData();
  formData.append('foto', this.foto!);
  formData.append('pruuId', pruuId);

  this.pruuService.salvarFotoPruu(formData).subscribe({
    next: () => {
      Swal.fire('Imagem carregada com sucesso!', '', 'success');
      this.voltar();
    },
    error: (erro) => {
      let erroString = this.transformarErroEmString(erro.error);

      Swal.fire('Erro ao fazer upload da imagem: ' + erroString, 'error');
    }
  });
}

public onFileSelected(event: any) {
  const file: File = event.target.files[0];
  if (file && file.size <= 10 * 1024 * 1024) { // Limite de 10MB
    this.foto = file;

    // Gerar o preview da imagem
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result; // Definir o preview da imagem
    };
    reader.readAsDataURL(file);
  } else {
    alert('Tamanho de arquivo não permitido! Máximo: 10MB.');
    this.foto = null;
    this.imagePreview = null; // Limpar o preview se não for um arquivo válido
  }
}


//Método para tratar Json de erros
private transformarErroEmString(erro: any): string {
  if (typeof erro === 'object') {
    return Object.entries(erro)
      .map(([key, value]) => `${key}: ${value}`)
      .join(', ');
  }
  return String(erro);
}

public voltar(): void {
  this.router.navigate(['/pruu']);
}



}
