import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../shared/model/usuario';
import { UsuarioService } from '../../shared/service/usuario.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-usuario-detalhe',
  templateUrl: './usuario-detalhe.component.html',
  styleUrl: './usuario-detalhe.component.sass'
})
export class UsuarioDetalheComponent implements OnInit {

  public usuario: Usuario = new Usuario();
  public fotoSelecionada: File | null = null;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  carregarUsuarioAutenticado(): void {
    this.usuarioService.buscarUsuarioAutenticado().subscribe({
      next: (usuario) => {
        this.usuario = usuario;
      },
      error: (erro) => {
        console.error('Erro ao carregar usuário autenticado:', erro);
        Swal.fire('Erro!', 'Não foi possível carregar os dados do usuário.', 'error');
      },
    });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.fotoSelecionada = file;
    }
  }

  salvarAlteracoes(): void {
    this.usuarioService.atualizarUsuario(this.usuario).subscribe({
      next: (usuarioAtualizado) => {
        this.usuario = usuarioAtualizado;
        Swal.fire('Sucesso!', 'Seus dados foram atualizados com sucesso.', 'success');
      },
      error: (erro) => {
        console.error('Erro ao atualizar usuário:', erro);
        Swal.fire('Erro!', 'Não foi possível atualizar seus dados.', 'error');
      },
    });

    if (this.fotoSelecionada) {
      this.usuarioService.salvarFotoPerfil(this.fotoSelecionada).subscribe({
        next: () => {
          Swal.fire('Sucesso!', 'Foto de perfil atualizada com sucesso.', 'success');
          this.fotoSelecionada = null; // Limpa a seleção de arquivo
        },
        error: (erro) => {
          console.error('Erro ao salvar foto de perfil:', erro);
          Swal.fire('Erro!', 'Não foi possível salvar sua foto de perfil.', 'error');
        },
      });
    }
  }
}



