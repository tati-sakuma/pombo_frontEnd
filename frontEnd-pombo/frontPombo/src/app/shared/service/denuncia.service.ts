import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../model/usuario';
import { Denuncia } from '../model/denuncia';

@Injectable({
  providedIn: 'root'
})
export class DenunciaService {

  private readonly API = 'http://localhost:8080/pombo/api/pruus';

  constructor(private httpClient: HttpClient) { }


  criarDenuncia(denuncia: Denuncia): Observable<Denuncia> {
    return this.httpClient.post<Denuncia>(this.API, denuncia);
  }

}
