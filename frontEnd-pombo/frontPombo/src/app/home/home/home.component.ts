import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../shared/model/usuario';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.sass'
})

export class HomeComponent implements OnInit{

  public usuarioAutenticado: Usuario;
  constructor(private router: Router) {}

  ngOnInit(): void {
    //this.carregarUsuarioAutenticado();
  }

  // carregarUsuarioAutenticado(): void {
  //   if (typeof localStorage !== 'undefined') {
  //     let usuarioNoStorage = localStorage.getItem('usuarioAutenticado');

  //     if (usuarioNoStorage) {
  //       this.usuarioAutenticado = JSON.parse(usuarioNoStorage);
  //     } else {
  //       this.router.navigate(['/login']);
  //     }
  //   } else {
  //     console.error('localStorage não está disponível. Não é possível carregar o usuário autenticado');
  //     this.router.navigate(['/login']);
  //   }
  // }

  // logout(): void {
  //   // Verifica se localStorage está disponível
  //   if (typeof localStorage !== 'undefined') {
  //     localStorage.removeItem('usuarioAutenticado');
  //   } else {
  //     console.error('localStorage is not available. Unable to remove authenticated user.');
  //     // Lida com a ausência de localStorage de acordo com a sua lógica
  //   }
  //   this.router.navigate(['/login']);
  // }
}



