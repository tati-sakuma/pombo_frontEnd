import { Denuncia } from './denuncia';
import { PruuDTO } from './dto/pruu.dto';
import { Usuario } from './usuario';

export class Pruu {
  id: string;
  usuario!: Usuario;
  likedByUsers: Usuario[] = [];
  denuncias: Denuncia[] = [];
  texto!: string;
  imagem?: string;
  bloqueado: boolean = false;
  excluido: boolean = false;
  dataHoraCriacao: Date = new Date();

  // Método estático para converter para DTO
  static paraDTO(
    pruu: Pruu,
    quantidadeLikes: number,
    quantidadeDenuncias: number,
    imagemPruu: string,
    fotoPerfil: string
  ): PruuDTO {
    return new PruuDTO(
      pruu.id,
      pruu.texto,
      imagemPruu,
      pruu.usuario.id,
      pruu.usuario.nome,
      fotoPerfil,
      quantidadeLikes,
      quantidadeDenuncias,
      pruu.dataHoraCriacao
    );
  }
}
