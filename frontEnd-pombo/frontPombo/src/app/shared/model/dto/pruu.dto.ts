export class PruuDTO {
  constructor(
    public id: string,
    public texto: string,
    public imagemPruu: string,
    public usuarioId: number,
    public usuarioNome: string,
    public fotoPerfil: string,
    public quantidadeLikes: number,
    public quantidadeDenuncias: number,
    public dataHoraCriacao: Date
  ) {}
}
