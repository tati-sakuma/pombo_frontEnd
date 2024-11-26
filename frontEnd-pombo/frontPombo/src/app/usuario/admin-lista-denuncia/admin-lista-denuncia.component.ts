import { Component, OnInit } from '@angular/core';
import { DenunciaService } from '../../shared/service/denuncia.service';
import { Router } from '@angular/router';
import { Denuncia, DenunciaDados } from '../../shared/model/denuncia';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-admin-lista-denuncia',
  templateUrl: './admin-lista-denuncia.component.html',
  styleUrls: ['./admin-lista-denuncia.component.sass']
})
export class AdminListaDenunciaComponent implements OnInit {
  denuncias: DenunciaDados[] = [];

  constructor(private denunciaService: DenunciaService, private router: Router) {}

  ngOnInit(): void {
    this.carregarDenuncias();
  }

  async carregarDenuncias () {
    let paginas = 1;
    while(true){
      let dados = await this.denunciaService.listarComFiltros({pagina:paginas, limite:3}).toPromise();

      if(dados != null && dados.length == 0){
        break;
      };

      this.denuncias = this.denuncias.concat(dados || []);
      paginas ++

    }
    console.log(this.denuncias);

  }

  navegarParaDetalhe(id: string) {
    this.router.navigate([`/usuario/admin/detalhe-denuncia`, id]);
  }
}
