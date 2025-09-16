package Human.Resource.Management.System.serviceImpl;


import Human.Resource.Management.System.model.UserDtls;
import Human.Resource.Management.System.repository.UserRepository;
import Human.Resource.Management.System.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDtls findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void save(UserDtls user) {
		userRepository.save(user);
	}
}
