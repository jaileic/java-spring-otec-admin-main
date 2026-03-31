package cl.talento.otec.admin.servicio;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.talento.otec.admin.modelo.Usuario;
import cl.talento.otec.admin.repositorio.UsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public JpaUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		return User.builder()
			.username(usuario.getUsername())
			.password(usuario.getPassword())
			.authorities(new SimpleGrantedAuthority(usuario.getRol()))
			.accountExpired(false)
			.accountLocked(false)
			.credentialsExpired(false)
			.disabled(!usuario.getActivo())
			.build();
	}
}
