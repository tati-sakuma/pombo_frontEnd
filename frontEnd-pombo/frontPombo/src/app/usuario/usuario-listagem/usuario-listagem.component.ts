import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../shared/model/usuario';
import { UsuarioService } from '../../shared/service/usuario.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-usuario-listagem',
  templateUrl: './usuario-listagem.component.html',
  styleUrl: './usuario-listagem.component.sass'
})
export class UsuarioListagemComponent implements OnInit {

  public usuarios: Usuario[] = [];

  constructor(private usuarioService: UsuarioService){}

  ngOnInit(): void {
    this.pesquisarTodosUsuarios();

  }

  private pesquisarTodosUsuarios () {
    this.usuarioService.pesquisarTodos().subscribe(
      resultado => {
        this.usuarios = resultado;
      },
    erro => {
        Swal.fire({
          icon: 'error',
          title: 'Erro ao listar todos usuários.',
          text: 'Erro ao listar todos usuários.' 
        });
      }
  )
  }
}
