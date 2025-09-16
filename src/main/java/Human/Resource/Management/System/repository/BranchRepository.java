package Human.Resource.Management.System.repository;

import Human.Resource.Management.System.model.Branch;
import Human.Resource.Management.System.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
}
