import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../model/usuario';
import { UsuarioDTO } from '../model/dto/usuario.dto';
import { UsuarioDetalheDTO } from '../model/dto/usuario.detalhe.dto';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private readonly API = 'http://localhost:8080/pombo/api/usuarios';

  constructor(private httpClient: HttpClient) {}

  public pesquisarTodos(): Observable<Array<Usuario>> {
    return this.httpClient.get<Array<Usuario>>(this.API + '/todos');
  }

  public salvarFotoPerfil(foto: File): Observable<void> {
    const formData = new FormData();
    formData.append('foto', foto);

    return this.httpClient.post<void>(`${this.API}/salvar-foto-perfil`,formData);
  }

  public buscarUsuarioAutenticado(): Observable<Usuario> {
    return this.httpClient.get<Usuario>(`${this.API}/usuario-autenticado`);
  }

  public getUsuarioPorId(id: number): Observable<UsuarioDTO> {
    return this.httpClient.get<UsuarioDTO>(`${this.API}/usuarios/${id}`);
  }

  public atualizarUsuario(usuarioDetalheDTO: UsuarioDetalheDTO): Observable<Usuario> {
    return this.httpClient.put<Usuario>(`${this.API}`, usuarioDetalheDTO);
  }

  excluirUsuario(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.API}/delete/${id}`);
  }


}
