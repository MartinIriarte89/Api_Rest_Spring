package com.martiniriarte.servicio;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.martiniriarte.modelo.UsuarioEntidad;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("UserDetailsService")
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final ServicioUsuario servicioUsuario;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioEntidad usuario = servicioUsuario.buscarPorNombreUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " no encontrado."));

		Collection<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.name())).collect(Collectors.toList());
		
		log.info(authorities.toString());

		return new User(usuario.getNombreUsuario(), usuario.getContrasena(), authorities);
	}

}
