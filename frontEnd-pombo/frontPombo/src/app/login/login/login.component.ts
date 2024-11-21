import { Component } from '@angular/core';
import { UsuarioDTO } from '../../shared/model/dto/usuario.dto';
import { LoginService } from '../../shared/service/login.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.sass'
})
export class LoginComponent {

  public dto: UsuarioDTO = new UsuarioDTO();

  constructor(
                private loginService: LoginService,
                private router: Router
              ) { }

  public login() {

    this.loginService.autenticar(this.dto)
    .subscribe({
      next: jwt => {
          Swal.fire('Sucesso', 'Usuário autenticado com sucesso', 'success');
          let token: string = jwt.body + "";
          localStorage.setItem('tokenUsuarioAutenticado', token);
          this.router.navigate(['/pruu']);
      },
      error: erro => {
        var mensagem: string;
        if(erro.status == 401){
          mensagem = "Usuário ou senha inválidos, tente novamente";
        }else{
          mensagem = erro.error;
        }

        Swal.fire('Erro', mensagem, 'error');
      }
    });
  }

  public cadastro() {
    this.router.navigate(['login/cadastro']);
  }
}
