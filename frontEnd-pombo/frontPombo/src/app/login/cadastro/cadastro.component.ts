import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../shared/service/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuario } from '../../shared/model/usuario';
import Swal from 'sweetalert2';
import { LoginService } from '../../shared/service/login.service';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.sass',
})
export class CadastroComponent implements OnInit {
  constructor(
    private loginService: LoginService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  public usuario: Usuario = new Usuario();

  ngOnInit(): void {

  }

  public salvarNovoUsuario() {
    this.loginService.salvar(this.usuario).subscribe({
      next: (resultado) => {
        this.usuario = resultado;
        Swal.fire('Cliente salvo com sucesso!').then((resultado) => {
          if (resultado.isConfirmed) {
            this.usuario = new Usuario();
          }
        });
      },
      error: (erro) => {
        Swal.fire({
          icon: 'error',
          title: 'Erro ao cadastrar novo usuário!',
          text: 'Erro ao cadastrar novo usuário:' + erro.error.mensagem,
        });
      },
    });
  }
}
