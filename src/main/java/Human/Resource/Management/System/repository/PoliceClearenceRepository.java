package Human.Resource.Management.System.repository;


import Human.Resource.Management.System.model.PoliceClearance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliceClearenceRepository extends JpaRepository<PoliceClearance, Integer> {
	boolean existsByReferID(String refId);
}
