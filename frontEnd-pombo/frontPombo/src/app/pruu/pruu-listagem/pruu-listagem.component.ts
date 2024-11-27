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
import { jwtDecode } from 'jwt-decode';
import { Denuncia } from '../../shared/model/denuncia';
import { MotivoDenuncia } from '../../shared/model/enum/motivo.denuncia';

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
  public loggedUserId: number;
  public denuncia: Denuncia

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
    this.pesquisarTodosUsuarios();

    let token;
    if(localStorage){
      token = localStorage.getItem('tokenUsuarioAutenticado');
    }

    if(token){
      let tokenJSON: any = jwtDecode(token);
      this.loggedUserId = tokenJSON?.userId;
    }
  }

  public pesquisar(): void {
    let pagina = this.pruuSeletor.pagina - 1; // Ajustar para zero-based index


    if (isNaN(pagina) || pagina < 0) {

      this.pruuSeletor.pagina = 1; // Reinicializa a página como 1
      return;
    }

    this.pruuService.listarComFiltros(this.pruuSeletor)
      .subscribe({
        next: (resultado: any) => {
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

public pesquisarTodosUsuarios(): void {
  this.usuarioService.pesquisarTodos().subscribe({
    next: (resultado) => {
      this.usuarios = resultado;
    },
    error: (erro) => {
      console.error('Erro ao listar os usuários:', erro);
      Swal.fire({
        title: 'Erro!',
        text: 'Erro ao consultar todos os pruus: ' + erro,
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

  public editarPruu(pruu: PruuDTO): void {
    Swal.fire({
      title: 'Editar Pruu',
      input: 'textarea',
      inputLabel: 'Edite o conteúdo do seu Pruu',
      inputValue: pruu.pruuConteudo, // Preenche o input com o conteúdo atual do Pruu
      showCancelButton: true,
      confirmButtonText: 'Salvar',
      cancelButtonText: 'Cancelar',
      inputValidator: (value) => {
        if (!value) {
          return 'O conteúdo não pode ser vazio!';
        }
        return null; // Retorna null se o valor é válido
      },
    }).then((result) => {
      if (result.isConfirmed) {
        let novoConteudo = result.value;
        // Chama o serviço para salvar as alterações
        this.pruuService.atualizarPruu(pruu.pruuId, novoConteudo).subscribe({
          next: () => {
            pruu.pruuConteudo = novoConteudo; // Atualiza a mensagem no frontend
            Swal.fire('Atualizado!', 'Sua mensagem foi atualizada com sucesso.', 'success');
          },
          error: (erro) => {
            console.error('Erro ao atualizar o Pruu:', erro);
            Swal.fire('Erro!', 'Houve um problema ao atualizar sua mensagem.', 'error');
          },
        });
      }
    });
  }

  public excluirPruu(pruu: PruuDTO): void {
    Swal.fire({
      title: 'Tem certeza?',
      text: 'Esta ação não pode ser desfeita!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar',
    }).then((resultado) => {
      if (resultado.isConfirmed) {
        this.pruuService.deletarPruu(pruu.pruuId).subscribe({
          next: () => {
            // Remove o Pruu da lista localmente
            this.pruus = this.pruus.filter((p) => p.pruuId !== pruu.pruuId);
            Swal.fire('Excluído!', 'O Pruu foi excluído com sucesso.', 'success');
          },
          error: (erro) => {
            console.error('Erro ao excluir o Pruu:', erro);
            Swal.fire('Erro!', 'Não foi possível excluir o Pruu.', 'error');
          },
        });
      }
    });
  }

  public denunciarPruu(pruu: PruuDTO): void {
    Swal.fire({
      title: 'Denunciar Pruu',
      input: 'select',
      inputOptions: {
        [MotivoDenuncia.SPAM]: 'Spam',
        [MotivoDenuncia.OFENSIVO]: 'Ofensivo',
        [MotivoDenuncia.FALSO]: 'Falso',
      },
      inputLabel: 'Selecione o motivo da denúncia',
      inputPlaceholder: 'Escolha um motivo',
      showCancelButton: true,
      confirmButtonText: 'Enviar',
      cancelButtonText: 'Cancelar',
      inputValidator: (value) => {
        if (!value) {
          return 'Você precisa selecionar um motivo para denunciar!';
        }
        return null;
      },
    }).then((resultado) => {
      if (resultado.isConfirmed) {
        this.denuncia = {
          motivo: resultado.value,
          pruu: {
            id: pruu.pruuId,
            texto: pruu.pruuConteudo,
            foto: pruu.pruuImagem || '',
            usuarioId: pruu.usuarioId,
            usuarioNome: pruu.usuarioNome,
            quantidadeLikes: pruu.quantidadeLikes,
            dataHoraCriacao: new Date(pruu.criadoEm),
            quantidadeDenuncias: pruu.quantidadeDenuncias
          } ,

        };

        this.denunciaService.criarDenuncia(this.denuncia).subscribe({
          next: (response) => {
            Swal.fire('Enviado!', 'Sua denúncia foi registrada.', 'success');
          },
          error: (erro) => {
            console.error('Erro ao denunciar:', erro);
            Swal.fire(
              'Erro!',
              'Ocorreu um problema ao registrar sua denúncia.',
              'error'
            );
          },
        });
      }
    });
  }

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
