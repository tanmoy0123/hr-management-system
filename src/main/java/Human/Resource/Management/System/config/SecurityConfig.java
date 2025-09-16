package Human.Resource.Management.System.config;

import Human.Resource.Management.System.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}


	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return customUserDetailsService.loadUserByUsername(username);
			}
		}; // Replace with your own implementation
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())  // Disable CSRF for testing
				.authorizeHttpRequests(auth -> auth
								.requestMatchers("/images/**", "/css/**", "/js/**").permitAll() // Updated : This was causing image not rendering to browser
								.requestMatchers("/admin/**").hasRole("ADMIN")
								.requestMatchers("/super-admin/**").hasRole("SUPER_ADMIN")
								.requestMatchers("/user/**").hasRole("USER")
		//						.requestMatchers("/**", "/login", "/register").permitAll()
								.requestMatchers("/login", "/register").permitAll()
								.anyRequest().authenticated()
				)
				.formLogin(form -> form

						.loginPage("/login")
						.defaultSuccessUrl("/dashboard/index", true)
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout=true")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.permitAll()
				)
				.sessionManagement(session -> session
						.maximumSessions(1)
						.expiredUrl("/login?expired=true")
				);

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		return new ProviderManager(List.of(new CustomAuthenticationProvider(userDetailsService, passwordEncoder())));
	}
}
