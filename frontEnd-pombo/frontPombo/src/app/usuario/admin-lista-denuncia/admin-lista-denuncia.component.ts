import { Component, OnInit } from '@angular/core';
import { DenunciaService } from '../../shared/service/denuncia.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-lista-denuncia',
  templateUrl: './admin-lista-denuncia.component.html',
  styleUrls: ['./admin-lista-denuncia.component.sass'],
})
export class AdminListaDenunciaComponent implements OnInit {
  denuncias: any[] = [];

  constructor(private denunciaService: DenunciaService, private router: Router) {}

  ngOnInit(): void {
    this.carregarDenuncias();
  }

  carregarDenuncias() {
    this.denunciaService.listarComFiltros({}).subscribe((dados) => {
      this.denuncias = dados;
    });
  }

  navegarParaDetalhe(id: string) {
    this.router.navigate([`/usuario/admin/detalhe-denuncia`, id]);
  }
}
