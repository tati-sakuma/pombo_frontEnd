import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsuarioDTO } from '../model/dto/usuario.dto';
import { Usuario } from '../model/usuario';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private readonly API = 'http://localhost:8080/pombo/auth';

  constructor(private httpClient: HttpClient) { }

  autenticar(dto: UsuarioDTO): Observable<HttpResponse<string>> {
    const authHeader = 'Basic ' + btoa(`${dto.login}:${dto.senha}`);
    const headers = new HttpHeaders({
       'Authorization': authHeader
    });

    /*
    if(response.status ===200){
        if(response.ehAdmin){
          this.router.navigate(['/admin']);
          }
    }
    */
    return this.httpClient.post<string>(this.API + "/authenticate", dto, {
      headers,
      observe: 'response',
      responseType: 'text' as 'json'
    });
  }

  sair() {
    localStorage.removeItem('tokenUsuarioAutenticado');
  }

  salvar(novoUsuario: Usuario): Observable<Usuario> {
    return this.httpClient.post<Usuario>(this.API + '/register', novoUsuario)
  }

}
