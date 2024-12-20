import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pruu } from '../model/pruu';
import { Observable } from 'rxjs';
import { PruuSeletor } from '../model/seletor/pruu.seletor';
import { PruuDTO } from '../model/dto/pruu.dto';
import { Page } from '../model/page';

@Injectable({
  providedIn: 'root'
})
export class PruuService {

  private readonly API = 'http://localhost:8080/pombo/api/pruus';

  constructor(private httpClient: HttpClient) { }


  public listarComFiltros(pruuSeletor: PruuSeletor): Observable<Page<PruuDTO>> {

    return this.httpClient.post<Page<PruuDTO>>(`${this.API}/filtros`, pruuSeletor);
  }

  public atualizarPruu(pruuId: string, novoConteudo: string): Observable<void> {
    const body = { pruuConteudo: novoConteudo }; // Define o payload esperado pelo backend
    return this.httpClient.put<void>(`${this.API}/atualizar/${pruuId}`, body);
  }

  public novoPruu(novoPruu: Pruu): Observable<Pruu> {
    return this.httpClient.post<Pruu>(this.API, novoPruu);
  }

  public salvarFotoPruu(formData: FormData): Observable<any> {
    return this.httpClient.post(`${this.API}/salvar-foto-pruu`, formData);
  }

  public darLike(pruuId: string): Observable<void> {
    return this.httpClient.post<void>(`${this.API}/dar-like/${pruuId}`, {});
  }

  deletarPruu(pruuId: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.API}/${pruuId}`);
  }



}
