package com.martiniriarte.seguridad;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.martiniriarte.error.exceptions.UsuariosNoEncontradosException;
import com.martiniriarte.modelo.UsuarioEntidad;
import com.martiniriarte.servicio.ServicioUsuario;
import com.martiniriarte.util.enumerados.RolUsuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";

	@Value("${jwt.secret}")
	private String jwtSecreto;

	@Value("${jwt.expiration}")
	private int jwtDuracionEnSeg;

	private final ServicioUsuario servicioUsuario;

	public String generarToken(Authentication authentication) {
		UsuarioEntidad usuario = servicioUsuario.buscarPorNombreUsuario(authentication.getName())
				.orElseThrow(UsuariosNoEncontradosException::new);

		Date fechaExpiracionToken = new Date(System.currentTimeMillis() + (jwtDuracionEnSeg * 1000));

		String rolesConFormato = usuario.getRoles().stream().map(RolUsuario::name).collect(Collectors.joining(", "));

		return Jwts.builder().signWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes()), SignatureAlgorithm.HS512)
				.setHeaderParam("type", TOKEN_TYPE).setSubject(Long.toString(usuario.getId())).setIssuedAt(new Date())
				.setExpiration(fechaExpiracionToken).claim("username", usuario.getNombreUsuario())
				.claim("roles", rolesConFormato).compact();

	}

	public Long obtenerIdDeJWT(String token) {
		JwtParser parseToken = getParseToken();
		Claims claims = parseToken.parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	private JwtParser getParseToken() {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecreto.getBytes())).build();
	}

	public boolean esValido(String authToken) {

		try {
			JwtParser parseToken = getParseToken();
			parseToken.parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			log.info("Error en la firma del token JWT: " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			log.info("Token malformado: " + ex.getMessage());
		} catch (ExpiredJwtException ex) {
			log.info("El token ha expirado: " + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			log.info("Token JWT no soportado: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.info("JWT claims vacío");
		}
		return false;

	}
}
