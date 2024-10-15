package br.senac.projeto_pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.senac.projeto_pombo.model.entity.Pruu;
import br.senac.projeto_pombo.model.entity.Usuario;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PruuRepository pruuRepository;

	@Test
	public void testeUsuarioSalvarSucesso() {
		Usuario usuario = new Usuario();
		usuario.setNome("Usuario válido");
		usuario.setCpf("05262192971");
		usuario.setEmail("tati@tati.com");
		usuario.setAdministrador(true);

		Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuario);

		assertNotNull(usuarioSalvo);
		assertThat(usuarioSalvo.getIdUsuario()).isNotNull();
	}

	@Test
	void falhaSalvarUsuarioComNomeEmBranco() {
		Usuario usuario = new Usuario();
		usuario.setNome("");
		usuario.setCpf("05262192971");
		usuario.setEmail("tati@tati.com");

		assertThrows(ConstraintViolationException.class, () -> {
			usuarioRepository.saveAndFlush(usuario);
		});
	}

	@Test
	void falhaSalvarUsuarioComNomeMuitoCurto() {
		Usuario usuario = new Usuario();
		usuario.setCpf("05262192971");
		usuario.setEmail("tati@tati.com");
		usuario.setNome("T");

		assertThrows(ConstraintViolationException.class, () -> {
			usuarioRepository.saveAndFlush(usuario);
		});
	}

	@Test
	void falhaSalvarUsuarioComNomeMuitoLongo() {
		Usuario usuario = new Usuario();
		usuario.setCpf("05262192971");
		usuario.setEmail("tati@tati.com");
		usuario.setNome("Nome muito longo ".repeat(20));

		assertThrows(ConstraintViolationException.class, () -> {
			usuarioRepository.saveAndFlush(usuario);
		});
	}

	@Test
	void falhaSalvarUsuarioComEmailEmBranco() {
		Usuario usuario = new Usuario();
		usuario.setCpf("05262192971");
		usuario.setEmail("");
		usuario.setNome("Usuario válido");

		assertThrows(ConstraintViolationException.class, () -> {
			usuarioRepository.saveAndFlush(usuario);
		});
	}

	@Test
	void falhaSalvarUsuarioComEmailInvalido() {
		Usuario usuario = new Usuario();
		usuario.setCpf("05262192971");
		usuario.setNome("Usuario válido");
		usuario.setEmail("email");

		assertThrows(ConstraintViolationException.class, () -> {
			usuarioRepository.saveAndFlush(usuario);
		});
	}

	@Test
	void falhaSalvarUsuarioComCpfEmBranco() {
		Usuario usuario = new Usuario();
		usuario.setCpf("");
		usuario.setEmail("tati@tati.com");
		usuario.setNome("Usuario válido");

		assertThrows(ConstraintViolationException.class, () -> {
			usuarioRepository.saveAndFlush(usuario);
		});
	}

	@Test
	void falhaSalvarUsuarioCpfInvalido() {
		Usuario usuario = new Usuario();
		usuario.setNome("Nome Valido");
		usuario.setEmail("tati@tati.com");
		usuario.setCpf("12345678900");

		assertThrows(ConstraintViolationException.class, () -> {
			usuarioRepository.saveAndFlush(usuario);
		});
	}

	@Test
	void deveRetornarTrueQuandoCpfExiste() {
		

		usuarioRepository.saveAndFlush(this.criarUsuario());
		assertTrue(usuarioRepository.cpfExiste("05262192971"), "Deve retornar true, pois o CPF já existe.");
	}

	@Test
	void deveRetornarFalseQuandoCpfNaoExiste() {
		String cpf = "09876543211";

		assertFalse(usuarioRepository.cpfExiste(cpf), "Deveria retornar false pois o CPF não existe.");
	}

	@Test
	void deveRetornarTrueQuandoUsuarioPossuiPruu() {

		Usuario usuarioSalvo = usuarioRepository.saveAndFlush(this.criarUsuario());

		Pruu pruu = new Pruu();
		pruu.setUsuario(usuarioSalvo);
		pruu.setMensagem("Conteúdo do Pruu");

		pruuRepository.saveAndFlush(pruu);

		assertTrue(usuarioRepository.verificarSePossuiPruu(usuarioSalvo.getIdUsuario()),
				"Deve retornar true, pois o usuário possui Pruu.");
	}

	@Test
	void deveRetornarFalseQuandoUsuarioNaoPossuiPruu() {

		Usuario usuarioSalvo = usuarioRepository.saveAndFlush(this.criarUsuario());

		assertFalse(usuarioRepository.verificarSePossuiPruu(usuarioSalvo.getIdUsuario()),
				"Deve retornar false, pois o usuário não possui Pruu.");
	}

	private Usuario criarUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome("Usuario válido");
		usuario.setCpf("05262192971");
		usuario.setEmail("tati@teste.com");

		return usuario;
	}
}
