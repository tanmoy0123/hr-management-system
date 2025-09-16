package Human.Resource.Management.System.repository;

import Human.Resource.Management.System.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyInformationRepository extends JpaRepository<Family, Long> {

//    List<Family> findByEmpId(String empId);

	boolean existsByRefId(String refID);

}
