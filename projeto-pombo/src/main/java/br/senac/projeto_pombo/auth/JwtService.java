package br.senac.projeto_pombo.auth;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import br.senac.projeto_pombo.model.entity.Usuario;

@Service
public class JwtService {
	 private final JwtEncoder jwtEncoder;
	 long dezHorasEmSegundos = 36000L;

	    public JwtService(JwtEncoder jwtEncoder) {
	        this.jwtEncoder = jwtEncoder;
	    }

	    public String getGenerateToken(Authentication authentication) {
	        Instant now = Instant.now();
	        String roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
	        
	        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();

	        JwtClaimsSet claims = JwtClaimsSet.builder()
	        		.issuer(roles)
	        		.issuedAt(now)
	        		.expiresAt(now.plusSeconds(dezHorasEmSegundos))
	        		.subject(authentication.getName())
	        		.claim("roles", roles)
	        		.claim("idUsuario", usuarioAutenticado.getIdUsuario())
	        		.build();
	        
	        
	        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	    }
}
