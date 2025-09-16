package Human.Resource.Management.System.repository;

import Human.Resource.Management.System.model.Wing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WingRepository extends JpaRepository<Wing, Long> {

}
