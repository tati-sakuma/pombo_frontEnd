import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DenunciaService } from '../../shared/service/denuncia.service';

@Component({
  selector: 'app-admin-detalhe-denuncia',
  templateUrl: './admin-detalhe-denuncia.component.html',
  styleUrls: ['./admin-detalhe-denuncia.component.sass'],
})
export class AdminDetalheDenunciaComponent implements OnInit {
  denuncia: any;

  constructor(
    private route: ActivatedRoute,
    private denunciaService: DenunciaService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.carregarDetalhe(id);
    }
  }

  carregarDetalhe(id: string) {
    this.denunciaService.buscarDenunciaPorId(id).subscribe((dados) => {
      this.denuncia = dados;
    });
  }

  atualizarSituacao(novaSituacao: string) {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.denunciaService
        .atualizarSituacao(id, novaSituacao)
        .subscribe(() => alert('Situação atualizada com sucesso!'));
    }
  }
}
