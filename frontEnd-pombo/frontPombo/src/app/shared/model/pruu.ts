import { Curtida } from "./curtida";
import { Usuario } from "./usuario";

export class Pruu {
  idPruu: string;
  mensagem: string;
  dataHoraPostagem: Date;
  curtidas: number;
  denuncias: number;
  bloqueado: boolean;
  excluido: boolean;
  usuario: Usuario;
  curtidasUsuarios: Curtida[];
}
