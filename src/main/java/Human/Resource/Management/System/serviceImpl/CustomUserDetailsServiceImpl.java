package Human.Resource.Management.System.serviceImpl;


import Human.Resource.Management.System.model.UserDtls;
import Human.Resource.Management.System.service.CustomUserDetailsService;
import Human.Resource.Management.System.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	private final UserService userService;

	public CustomUserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDtls user = userService.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found: " + username);
		}

		return new User(
				user.getUsername(),
				user.getPassword(),
				Collections.singletonList(() -> "ROLE_" + user.getRole()) // Ensuring Spring Security role format
		);
	}
}
