import { TiposDenuncia } from "./enum/tipos-denuncia";
import { Pruu } from "./pruu";
import { Usuario } from "./usuario";

export class Denuncia {
  idDenuncia: string;
  usuario: Usuario;
  pruu: Pruu;
  motivo: TiposDenuncia;
  dataDenuncia: Date;
  analisada: boolean = false;
}
