

package Human.Resource.Management.System.repository;

import Human.Resource.Management.System.model.WingDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WingDivisionRepository extends JpaRepository<WingDivision, Long> {

	List<WingDivision> findWingDivisionByWing_WingName(String wingName);
	WingDivision findFirstByWing_WingName(String wingName);
}

