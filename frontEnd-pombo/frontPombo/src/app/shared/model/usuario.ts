import { Curtida } from "./curtida";

export class Usuario {
  idUsuario: number;
  nome: string;
  email: string;
  cpf: string;
  senha: string;
  administrador: boolean;
  imagemEmBase64: string;
  pruusCurtidos: Curtida[];

  getAuthorities(): string[] {
    return [this.administrador ? 'ADMINISTRADOR' : 'USUARIO'];
  }

  getPassword(): string {
    return this.senha;
  }

  getUsername(): string {
    return this.cpf;
  }
}
