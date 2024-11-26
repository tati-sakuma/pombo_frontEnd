import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Denuncia } from '../model/denuncia';

@Injectable({
  providedIn: 'root',
})
export class DenunciaService {
  private readonly API = 'http://localhost:8080/pombo/api/denuncias';

  constructor(private httpClient: HttpClient) {}


  criarDenuncia(denuncia: Denuncia): Observable<Denuncia> {
    return this.httpClient.post<Denuncia>(this.API, denuncia);}

  listarComFiltros(seletor: any): Observable<any[]> {
    return this.httpClient.post<any[]>(`${this.API}/filtros`, seletor);
  }

  buscarDenunciaPorId(id: string): Observable<any> {
    return this.httpClient.get<any>(`${this.API}/${id}`);
  }

  atualizarSituacao(id: string, novaSituacao: string): Observable<void> {
    return this.httpClient.patch<void>(
      `${this.API}/admin/atualizar-situacao/${id}/${novaSituacao}`,
      {}
    );
  }
}
