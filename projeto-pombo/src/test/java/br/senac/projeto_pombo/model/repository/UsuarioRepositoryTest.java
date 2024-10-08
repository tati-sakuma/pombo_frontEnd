package br.senac.projeto_pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.senac.projeto_pombo.model.entity.Usuario;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void testeUsuarioSalvarSucesso() {
		Usuario usuario = new Usuario();
		usuario.setNome("Usuario válido");
		usuario.setCpf("05262192971");
		usuario.setEmail("tati@tati.com");
		usuario.setAdministrador(true);
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		assertNotNull(usuarioSalvo);
		assertThat(usuarioSalvo.getIdUsuario()).isNotNull();
	}
	
	@Test
	public void testeSalvarDadosCorretos () {
		
	}
	
	
}
