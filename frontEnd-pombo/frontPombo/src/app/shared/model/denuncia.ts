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

export class DenunciaDados {
  denunciaId: string;
  denuncianteId: number;
  denuncianteNome: string;
  denunciaMotivo: string;
  denunciaSituacao: string;
  pruuId: string;
  dataCriacao: Date;
}
