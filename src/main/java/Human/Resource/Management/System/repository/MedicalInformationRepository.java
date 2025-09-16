

package Human.Resource.Management.System.repository;


import Human.Resource.Management.System.model.Medical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalInformationRepository extends JpaRepository<Medical, Long> {
    List<Medical> findByEmployeeBasicInformation_Id(Long employeeId);

    boolean existsByReferID(String refId);
}
