import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../shared/model/usuario';
import { UsuarioService } from '../../shared/service/usuario.service';
import Swal from 'sweetalert2';
import { UsuarioDetalheDTO } from '../../shared/model/dto/usuario.detalhe.dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usuario-detalhe',
  templateUrl: './usuario-detalhe.component.html',
  styleUrl: './usuario-detalhe.component.sass',
})
export class UsuarioDetalheComponent implements OnInit {
  public usuario: Usuario = new Usuario();
  public fotoSelecionada: File | null = null;
  public usuarioDetalheDto: UsuarioDetalheDTO = new UsuarioDetalheDTO();

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,

  ) {}

  ngOnInit(): void {
    this.carregarUsuarioAutenticado();
  }

  public carregarUsuarioAutenticado(): void {
    this.usuarioService.buscarUsuarioAutenticado().subscribe({
      next: (usuario) => {
        this.usuario = usuario;
      },
      error: (erro) => {
        console.error('Erro ao carregar usuário autenticado:', erro);
        Swal.fire(
          'Erro!',
          'Não foi possível carregar os dados do usuário.',
          'error'
        );
      },
    });
  }

  public onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.fotoSelecionada = file;
    }
  }

  public editarUsuario(): void {
    this.usuarioDetalheDto = {
      id: this.usuario.id,
      nome: this.usuario.nome,
      email: this.usuario.email,
      senha: this.usuario.password,
    };

    this.usuarioService.atualizarUsuario(this.usuarioDetalheDto).subscribe({
      next: (usuarioAtualizado: Usuario) => {
        this.usuario.id = usuarioAtualizado.id;
        this.usuario.nome = usuarioAtualizado.nome;
        this.usuario.email = usuarioAtualizado.email;
        this.usuario.password = usuarioAtualizado.password;

        Swal.fire(
          'Sucesso!',
          'Seus dados foram atualizados com sucesso.',
          'success'
        );
      },
      error: (erro) => {
        console.error('Erro ao atualizar usuário:', erro);
        Swal.fire('Erro!', 'Não foi possível atualizar seus dados.', 'error');
      },
    });

    if (this.fotoSelecionada) {
      this.usuarioService.salvarFotoPerfil(this.fotoSelecionada).subscribe({
        next: () => {
          Swal.fire(
            'Sucesso!',
            'Foto de perfil atualizada com sucesso.',
            'success'
          );
          this.fotoSelecionada = null; // Limpa a seleção de arquivo
        },
        error: (erro) => {
          console.error('Erro ao salvar foto de perfil:', erro);
          Swal.fire(
            'Erro!',
            'Não foi possível salvar sua foto de perfil.',
            'error'
          );
        },
      });
    }
  }

  public excluirUsuario(): void {
    Swal.fire({
      title: 'Tem certeza?',
      text: 'Você não poderá reverter essa ação!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.usuarioService.excluirUsuario(this.usuario.id).subscribe({
          next: () => {
            Swal.fire('Excluído!', 'Seu usuário foi excluído com sucesso.', 'success').then(() => {
              localStorage.removeItem('tokenUsuarioAutenticado'); // Remove o token após exclusão
              this.router.navigate(['/login']); // Redireciona para a tela de login
            });
          },
          error: (erro) => {
            console.error('Erro ao excluir usuário:', erro);
            Swal.fire(
              'Erro!',
              erro.error?.message || 'Não foi possível excluir o usuário.',
              'error'
            );
          },
        });
      }
    });
  }
}
