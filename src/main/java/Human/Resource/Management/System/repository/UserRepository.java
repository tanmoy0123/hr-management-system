package Human.Resource.Management.System.repository;


import Human.Resource.Management.System.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDtls, Integer> {
	UserDtls findByUsername(String username);
}
