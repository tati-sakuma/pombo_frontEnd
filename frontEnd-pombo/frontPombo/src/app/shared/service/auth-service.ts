import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private ehAdministrador: boolean = false;

  constructor() {
    this.updateUserRole();
  }

  updateUserRole(): void {
    const token = localStorage.getItem('tokenUsuarioAutenticado');
    if (token) {
      const tokenJSON: any = jwtDecode(token);
      this.ehAdministrador = tokenJSON?.roles === 'ADMIN';
    } else {
      this.ehAdministrador = false;
    }
  }

  isAdmin(): boolean {
    return this.ehAdministrador;
  }
}
