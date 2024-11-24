import { Component, OnInit } from '@angular/core';
import { Pruu } from '../../shared/model/pruu';
import { PruuService } from '../../shared/service/pruu.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { PruuSeletor } from '../../shared/model/seletor/pruu.seletor';
import { PruuDTO } from '../../shared/model/dto/pruu.dto';
import { UsuarioService } from '../../shared/service/usuario.service';
import { Usuario } from '../../shared/model/usuario';

@Component({
  selector: 'app-pruu-listagem',
  templateUrl: './pruu-listagem.component.html',
  styleUrl: './pruu-listagem.component.sass'
})
export class PruuListagemComponent implements OnInit {

  public pruu: Pruu;
  public pruus: PruuDTO[] = [];
  public pruuSeletor = new PruuSeletor;
  public usuarios: Usuario[] = [];
  public totalPaginas: number = 0;
  public readonly TAMANHO_PAGINA: number = 3;
  public paginas: number[] = [];


  constructor(private pruuService: PruuService,
              private usuarioService: UsuarioService,
              private router: Router) {}

  ngOnInit(): void {
    this.pruuSeletor.pagina = 1;
    this.pruuSeletor.limite = this.TAMANHO_PAGINA;
    //this.pesquisar();
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

  public atualizarLike(pruu: PruuDTO) {
    this.pruuService.darLike(pruu.pruuId).subscribe({
      next: () => {
        pruu.estaCurtido = !pruu.estaCurtido; // Alterna o estado de curtida
        pruu.quantidadeLikes += pruu.estaCurtido ? 1 : -1; // Atualiza a quantidade
      },
      error: (erro) => {
        console.error('Erro ao curtir/descurtir:', erro);
      },
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

  public mudarPagina(pagina: number): void {
    if (pagina < 1 || pagina > this.totalPaginas) {
      return; // Evita números inválidos
    }
    this.pruuSeletor.pagina = pagina;
    this.pesquisar(); // Recarrega os dados
  }
}
