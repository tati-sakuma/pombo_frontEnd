import { BaseSeletor } from "./base.seletor";

export class PruuSeletor extends BaseSeletor {
  texto?: string;
  dataInicioCriacao?: string;
  dataFimCriacao?: string;
  usuarioId?: number;
  usuarioNome?: string;
  excluido?: string;
  bloqueado?: string;
  estaCurtido: boolean;
}
