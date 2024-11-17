import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsuarioDTO } from '../model/dto/usuario.dto';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private readonly API = 'http://localhost:8080/auth';

  constructor(private httpClient: HttpClient) { }

  autenticar(dto: UsuarioDTO): Observable<HttpResponse<string>> {
    const authHeader = 'Basic ' + btoa(`${dto.login}:${dto.senha}`);
    const headers = new HttpHeaders({
       'Authorization': authHeader
    });

    return this.httpClient.post<string>(this.API + "/authenticate", dto, {
      headers,
      observe: 'response',
      responseType: 'text' as 'json'
    });
  }

  sair() {
    localStorage.removeItem('tokenUsuarioAutenticado');
  }
}
