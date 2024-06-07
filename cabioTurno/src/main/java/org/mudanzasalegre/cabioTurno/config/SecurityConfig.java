package org.mudanzasalegre.cabioTurno.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	UserDetailsManager usersCustom(DataSource dataSource) {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery("select username, pass, estatus from usuarios u where username=?");
		users.setAuthoritiesByUsernameQuery(
				"select u.username, p.nombre from usuarioperfil up " + "inner join usuarios u on u.id = up.idUsuario "
						+ "inner join perfiles p on p.id = up.idPerfil " + "where u.username=?");
		return users;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations(), new AntPathRequestMatcher("/login"),
								new AntPathRequestMatcher("/"), new AntPathRequestMatcher("/home"))
						.permitAll().requestMatchers("/css/**", "/fonts/**", "/js/**", "/images/**", "/libs/**").permitAll()
						.requestMatchers("/bcrypt/**").permitAll().requestMatchers("/ws/**") // Permitir acceso a WebSocket
						.permitAll().requestMatchers(new AntPathRequestMatcher("/usuarios/**"), new AntPathRequestMatcher("/perfiles/**"))
						.hasAuthority("Administrador").requestMatchers(new AntPathRequestMatcher("/cambioTurno/form"))
						.hasAnyAuthority("Administrador", "Operador", "Celador")
						.requestMatchers(new AntPathRequestMatcher("/cambioTurno/save"))
						.hasAnyAuthority("Administrador", "Operador", "Celador")
						.requestMatchers(new AntPathRequestMatcher("/cambioTurno/list")).hasAuthority("Administrador")
						.requestMatchers(new AntPathRequestMatcher("/marcarNotificacionesComoLeidas")).authenticated() // Permitir acceso
																																																																																																					// autenticado a
																																																																																																					// /marcarNotificacionesComoLeidas
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/logout-success").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
