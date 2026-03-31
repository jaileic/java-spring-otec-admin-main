package cl.talento.otec.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/", "/login", "/error", "/css/**", "/js/**", "/img/**").permitAll()
				.requestMatchers("/cursos/nuevo", "/cursos/editar/**", "/cursos/eliminar/**", "/cursos/restaurar/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/cursos", "/api/cursos/**").hasRole("ADMIN")
				.requestMatchers("/cursos/**", "/estudiantes/**", "/practicas/**", "/evaluaciones/**", "/relatores/**").hasAnyRole("ADMIN", "USER")
				.requestMatchers("/api/**").authenticated()
				.anyRequest().permitAll())
			.formLogin(login -> login
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/estudiantes", true)
				.failureUrl("/login?error=true"))
			.logout(logout -> logout
				.logoutSuccessUrl("/login?logout=true")
				.permitAll())
			.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
