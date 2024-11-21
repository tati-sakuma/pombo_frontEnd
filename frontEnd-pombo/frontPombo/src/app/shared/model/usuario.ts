import { Denuncia } from "./denuncia";
import { Role } from "./enum/role";
import { Pruu } from "./pruu";

export class Usuario {
  id: number;
  nome!: string;
  email!: string;
  cpf!: string;
  foto?: string;
  password!: string;
  role: Role = Role.USER;
  pruus: Pruu[] = [];
  denuncias: Denuncia[] = [];
  criadoEm?: Date;

  constructor(init?: Partial<Usuario>) {
    Object.assign(this, init);
  }

  getAuthorities(): string[] {
    return [this.role]; // Retorna o papel do usuário como uma lista
  }
  getUsername(): string {
    return this.email;
  }
  getPassword(): string {
    return this.password;
  }
}
