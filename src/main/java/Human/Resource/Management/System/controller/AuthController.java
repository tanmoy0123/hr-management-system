package Human.Resource.Management.System.controller;

import Human.Resource.Management.System.model.UserDtls;
import Human.Resource.Management.System.service.UserService;
import Human.Resource.Management.System.serviceImpl.CustomUserDetailsServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final CustomUserDetailsServiceImpl customUserDetailsService;

	public AuthController(UserService userService, BCryptPasswordEncoder passwordEncoder, CustomUserDetailsServiceImpl customUserDetailsService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.customUserDetailsService = customUserDetailsService;
	}

	@GetMapping("/signup")
	public String showSignupPage(Model model) {
		model.addAttribute("user", new UserDtls());
//        return "signup";

		return "register/signup";  // Returns the view name
	}

	@PostMapping("/signup")
	public String signupUser(UserDtls user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encode the password
		userService.save(user);  // Save the user in the database
		return "redirect:/login";  // Redirect to login page after successful signup
	}

	@GetMapping("/login")
	public String showSigninPage() {
//        return "signin";
		return "Login/signin";  // Returns the view name
	}
}
