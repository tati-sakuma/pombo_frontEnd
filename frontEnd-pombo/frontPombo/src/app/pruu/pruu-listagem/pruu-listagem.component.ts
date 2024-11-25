import { Component, OnInit } from '@angular/core';
import { Pruu } from '../../shared/model/pruu';
import { PruuService } from '../../shared/service/pruu.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { PruuSeletor } from '../../shared/model/seletor/pruu.seletor';
import { PruuDTO } from '../../shared/model/dto/pruu.dto';
import { UsuarioService } from '../../shared/service/usuario.service';
import { Usuario } from '../../shared/model/usuario';
import { DenunciaService } from '../../shared/service/denuncia.service';

@Component({
  selector: 'app-pruu-listagem',
  templateUrl: './pruu-listagem.component.html',
  styleUrl: './pruu-listagem.component.sass',
})
export class PruuListagemComponent implements OnInit {
  public pruu: Pruu;
  public pruus: PruuDTO[] = [];
  public pruuSeletor = new PruuSeletor();
  public usuarios: Usuario[] = [];
  public totalPaginas: number = 0;
  public paginaAtual: number = 0;
  public readonly TAMANHO_PAGINA: number = 10;
  public loggedUserId: number; // Substitua isso pela lógica de obter o ID do usuário autenticado

  constructor(
    private pruuService: PruuService,
    private usuarioService: UsuarioService,
    private denunciaService: DenunciaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.pruuSeletor.pagina = 1;
    this.pruuSeletor.limite = this.TAMANHO_PAGINA;
    this.pesquisar();
  }

  public pesquisar(): void {
    let pagina = this.pruuSeletor.pagina - 1; // Ajustar para zero-based index

    if (isNaN(pagina) || pagina < 0) {
      console.error('Página inválida detectada:', pagina);
      this.pruuSeletor.pagina = 1; // Reinicializa a página como 1
      return;
    }

    this.pruuService
      .listarComFiltros(
        this.pruuSeletor,
        this.pruuSeletor.pagina - 1,
        this.TAMANHO_PAGINA
      )
      .subscribe({
        next: (resultado: any) => {
          console.log('Página Atual:', this.paginaAtual);
          console.log('Total de Páginas:', this.totalPaginas);
          this.pruus = resultado.content;
          this.totalPaginas = resultado.totalPages;
          this.paginaAtual = resultado.number + 1;
        },
        error: (erro) => {
          let erroString = this.transformarErroEmString(erro.error);
          Swal.fire({
            title: 'Erro!',
            text: 'Erro ao consultar todos os pruus: ' + erroString,
            icon: 'error',
          });
        },
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

  public isLoggedUser(pruu: PruuDTO): boolean {
    return pruu.usuarioId === this.loggedUserId;
  }

  public editarPruu(pruu: PruuDTO) {
    Swal.fire(
      'Editar',
      `Editar funcionalidade para o Pruu: ${pruu.pruuId}`,
      'info'
    );
    // Lógica para abrir um modal ou redirecionar para a página de edição
  }

  public excluirPruu(pruu: PruuDTO) {
    Swal.fire({
      title: 'Tem certeza?',
      text: 'Você não poderá reverter isso!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.pruuService.deletarPruu(pruu.pruuId).subscribe(() => {
          Swal.fire('Excluído!', 'Seu Pruu foi excluído.', 'success');
          this.pruus = this.pruus.filter((p) => p.pruuId !== pruu.pruuId);
        });
      }
    });
  }

  // public denunciarPruu() {
  //   Swal.fire({
  //     title: 'Denunciar Pruu',
  //     input: 'textarea',
  //     inputLabel: 'Explique o motivo da denúncia',
  //     inputPlaceholder: 'Escreva aqui...',
  //     showCancelButton: true,
  //     confirmButtonText: 'Enviar',
  //     cancelButtonText: 'Cancelar'
  //   }).then((result) => {
  //     if (result.isConfirmed) {
  //       this.denunciaService.criarDenuncia(pruu.pruuId).subscribe(() => {
  //         Swal.fire('Enviado!', 'Sua denúncia foi registrada.', 'success');
  //       });
  //     }
  //   });
  // }

  public limpar(): void {
    this.pruuSeletor = new PruuSeletor();
    this.pruuSeletor.pagina = 1; // Sempre resetar para página 1
    this.pruuSeletor.limite = this.TAMANHO_PAGINA;
    this.paginaAtual = 0; // Reinicia a página atual
    this.pesquisar();
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
    if (pagina < 0 || pagina >= this.totalPaginas) {
      console.warn('Tentativa de acessar uma página inválida:', pagina);
      return; // Interrompe a execução se a página for inválida
    }

    this.pruuSeletor.pagina = pagina + 1; // Atualiza para índice baseado em 1
    this.paginaAtual = pagina; // Atualiza a página atual para exibição
    this.pesquisar(); // Faz nova pesquisa
  }
}
