package Human.Resource.Management.System.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


		System.out.println(encoder.encode("1234"));
		System.out.println(encoder.encode("1234"));

	}
}