import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Usuario } from '../../shared/model/usuario';
import { UsuarioService } from '../../shared/service/usuario.service';
@Component({
  selector: 'app-admin-gerenciar-usuarios',
  templateUrl: './admin-gerenciar-usuarios.component.html',
  styleUrl: './admin-gerenciar-usuarios.component.sass'
})
export class AdminGerenciarUsuariosComponent implements OnInit {
  usuarios:Usuario[] = [];

  constructor(private usuarioService:UsuarioService, private router:Router){}

  ngOnInit(){
    this.carregarUsuarios();
  }

  async carregarUsuarios(){
     const dados =await this.usuarioService.pesquisarTodos().toPromise();
     if(dados!=null &&dados.length>0){
        this.usuarios = dados;
     }
  }

  navegarParaEdicao(id:number){

  }

}
