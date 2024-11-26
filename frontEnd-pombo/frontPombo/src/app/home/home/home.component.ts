import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { Usuario } from '../../shared/model/usuario';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { isPlatformBrowser } from '@angular/common';
import { AuthService } from '../../shared/service/auth-service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.sass',
})
export class HomeComponent implements OnInit {
  public ehAdministrador: boolean = false;

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    let token;

    if (localStorage) {
      token = localStorage.getItem('tokenUsuarioAutenticado');
    }

    if (token) {
      const tokenJSON: any = jwtDecode(token);
      this.ehAdministrador = tokenJSON?.roles === 'ADMIN';
    } else {
      this.router.navigate(['/login']);
    }
  }

  public logout() {
    localStorage.removeItem('tokenUsuarioAutenticado');
    this.router.navigate(['/login']);
  }
}
