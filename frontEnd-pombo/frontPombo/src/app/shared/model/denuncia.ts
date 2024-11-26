import { MotivoDenuncia } from "./enum/motivo.denuncia";
import { Pruu } from "./pruu";
import { Usuario } from "./usuario";

export class Denuncia {
  idDenuncia?: string;
  usuario?: Usuario;
  pruu: Pruu;
  motivo: MotivoDenuncia;
  dataDenuncia?: Date;
  analisada?: boolean = false;
}
