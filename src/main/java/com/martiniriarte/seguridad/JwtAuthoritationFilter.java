package com.martiniriarte.seguridad;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.martiniriarte.modelo.UsuarioEntidad;
import com.martiniriarte.servicio.ServicioUsuario;
import com.martiniriarte.servicio.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthoritationFilter extends OncePerRequestFilter{
	
	private final JwtProvider jwtProvider;
	private final UserDetailsServiceImpl detailsServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			String token = getTokenDeLaRequest(request);
			
			if(StringUtils.hasText(token) && jwtProvider.esValido(token)) {
				Long usuarioId = jwtProvider.obtenerIdDeJWT(token);
				UserDetails userDetails = detailsServiceImpl.cargarUsuarioPorId(usuarioId);				
				List<String> roles = userDetails.getAuthorities().stream().map(granted -> granted.getAuthority()).collect(Collectors.toList());
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, roles, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			
		}catch (Exception e) {
			log.info("No se ha podido establecer la autenticación de usuario en el contexto de seguridad");
		}
		
		filterChain.doFilter(request, response);
	}

	private String getTokenDeLaRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)){
			return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length(), bearerToken.length());
		}
		
		return null;
	}

	
}
