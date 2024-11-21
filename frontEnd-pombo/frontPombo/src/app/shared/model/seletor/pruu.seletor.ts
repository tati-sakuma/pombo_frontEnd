import { BaseSeletor } from "./base.seletor";

export class PruuSeletor extends BaseSeletor {
  texto?: string;
  dataInicioCriacao?: Date;
  dataFimCriacao?: Date;
  usuarioId?: number;
  estaCurtido?: boolean;
}
