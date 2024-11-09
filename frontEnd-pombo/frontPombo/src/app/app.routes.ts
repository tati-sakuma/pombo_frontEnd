import { Routes } from '@angular/router';

export const routes: Routes = [
  {path: '', redirectTo: 'usuario', pathMatch: 'full'},
  {
    path: 'usuario',
    loadChildren: () =>
      import('./usuario/usuario.module').then((m)=> m.UsuarioModule),

  }

];
