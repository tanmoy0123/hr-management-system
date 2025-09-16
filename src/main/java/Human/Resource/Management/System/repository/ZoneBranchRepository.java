

package Human.Resource.Management.System.repository;

import Human.Resource.Management.System.model.WingDivision;
import Human.Resource.Management.System.model.ZoneBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneBranchRepository extends JpaRepository<ZoneBranch, Long> {
	List<ZoneBranch> findZoneBranchByZone_ZoneName(String zoneName);
	ZoneBranch findFirstByZone_ZoneName(String zoneName);
}
