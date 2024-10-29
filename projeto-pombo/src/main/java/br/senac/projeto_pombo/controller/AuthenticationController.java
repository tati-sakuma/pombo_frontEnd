package br.senac.projeto_pombo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.senac.projeto_pombo.auth.AuthenticationService;
import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.service.UsuarioService;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Método de login padronizado -> Basic Auth
	 * 
	 * O parâmetro Authentication já encapsula login (username) e senha (password)
	 * Basic <Base64 encoded username and password>
	 * 
	 * @param authentication
	 * @return o JWT gerado
	 */
	@PostMapping("authenticate")
	public String authenticate(Authentication authentication) {
		return authenticationService.authenticate(authentication);
	}

	@PostMapping("/novo-usuario")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void registrarJogador(@RequestBody Usuario novoUsuario) throws PomboException {

		String senhaCifrada = passwordEncoder.encode(novoUsuario.getSenha());

		novoUsuario.setSenha(senhaCifrada);
		novoUsuario.setCpf(novoUsuario.getCpf());

		usuarioService.salvar(novoUsuario);
	}
}
