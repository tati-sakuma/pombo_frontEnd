package model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.model.repository.UsuarioRepository;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void testeCpfExiste () {
		Usuario usuario = new Usuario();
		usuario.setNome("Usuario válido");
		
	}
	

}
