import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pruu } from '../model/pruu';
import { Observable } from 'rxjs';
import { PruuSeletor } from '../model/seletor/pruu.seletor';

@Injectable({
  providedIn: 'root'
})
export class PruuService {

  private readonly API = 'http://localhost:8080/api/pruus';

  constructor(private httpClient: HttpClient) { }


  public listarComFiltros(pruuSeletor: PruuSeletor): Observable<Array<Pruu>> {
    return this.httpClient.post<Array<Pruu>>(this.API + "/filtros", pruuSeletor);
  }

  public novoPruu(novoPruu: Pruu): Observable<Pruu> {
    return this.httpClient.post<Pruu>(this.API, novoPruu);
  }

}
