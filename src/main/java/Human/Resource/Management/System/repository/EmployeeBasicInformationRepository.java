package Human.Resource.Management.System.repository;


import Human.Resource.Management.System.model.EmployeeBasicInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeBasicInformationRepository extends JpaRepository<EmployeeBasicInformation, Integer> {

//    List<EmployeeBasicInformation> findByNidDocumentPath(byte[] nidDocumentPath);

	@Query("SELECT COALESCE(MAX(e.employeeId), 200000) FROM EmployeeBasicInformation e WHERE e.employeeId>199999 AND " +
			"e.employeeId<300000")
	Integer permanentEmployeeId();

	@Query("SELECT COALESCE(MAX(e.employeeId), 300000) FROM EmployeeBasicInformation e WHERE e.employeeId>299999 AND " +
			"e.employeeId<400000")
	Integer temporaryEmployeeId();

	@Query("SELECT COALESCE(MAX(e.employeeId), 400000) FROM EmployeeBasicInformation e WHERE e.employeeId>399999 AND " +
			"e.employeeId<500000")
	Integer contractEmployeeId();

//	@Query("SELECT e FROM EmployeeBasicInformation e WHERE e.id = :id")
//	EmployeeBasicInformation testQuery(@Param("id") int id);


	EmployeeBasicInformation findByEmployeeId(Integer employeeId);


}


