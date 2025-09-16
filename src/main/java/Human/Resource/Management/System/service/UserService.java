package Human.Resource.Management.System.service;

import Human.Resource.Management.System.model.UserDtls;


public interface UserService {
	UserDtls findByUsername(String username);

	void save(UserDtls user);
}
