import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { Usuario } from '../../shared/model/usuario';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.sass'
})

export class HomeComponent implements OnInit{

  public idUsuarioAutenticado: number;
  public ehAdministrador: boolean = false;

  constructor(private router: Router,
  ) {}

  ngOnInit(): void {
    let token;

    if(localStorage){
      token = localStorage.getItem('tokenUsuarioAutenticado');
    }

    if(token){
      let tokenJSON: any = jwtDecode(token);
      this.ehAdministrador = tokenJSON?.roles == 'ADMIN';

      if(this.ehAdministrador){
        this.router.navigate(['/home/cartas']);
      }
    } else {
     this.router.navigate(['/login']);
    }
  }

  logout(){
    localStorage.removeItem('tokenUsuarioAutenticado');
    this.router.navigate(['/login']);
  }
}




