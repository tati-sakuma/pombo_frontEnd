import { MotivoDenuncia } from "../enum/motivo.denuncia";
import { SituacaoDenuncia } from "../enum/situacao.denuncia";
import { BaseSeletor } from "./base.seletor";

export class DenunciaSeletor extends BaseSeletor {
  usuarioId?: number;
  pruuId?: string;
  motivoDenuncia?: MotivoDenuncia;
  situacaoDenuncia?: SituacaoDenuncia;
  dataInicio?: Date;
  dataFinal?: Date;

}
