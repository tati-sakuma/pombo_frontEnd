import { Denuncia } from './denuncia';
import { PruuDTO } from './dto/pruu.dto';
import { Usuario } from './usuario';

export class Pruu {
  id!: string;
  texto!: string;
  foto: string;
  usuarioId!: number;
  usuarioNome!: string;
  quantidadeLikes!: number;
  quantidadeDenuncias!: number;
  dataHoraCriacao!: Date;

  // Método estático para converter para DTO
  static fromDTO(dto: PruuDTO): Pruu {
    const pruu = new Pruu();
    pruu.id = dto.pruuId;
    pruu.texto = dto.pruuConteudo;
    pruu.foto = dto.pruuImagem;
    pruu.usuarioId = dto.usuarioId;
    pruu.usuarioNome = dto.usuarioNome;
    pruu.quantidadeLikes = dto.quantidadeLikes;
    pruu.quantidadeDenuncias = dto.quantidadeDenuncias;
    pruu.dataHoraCriacao = new Date(dto.criadoEm);
    return pruu;
  }
}
