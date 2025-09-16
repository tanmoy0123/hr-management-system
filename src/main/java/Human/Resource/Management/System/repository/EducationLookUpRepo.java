package Human.Resource.Management.System.repository;

import Human.Resource.Management.System.model.EducationLookUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationLookUpRepo extends JpaRepository<EducationLookUp, Long> {
	List<EducationLookUp> findByLevelOfEducation(String levelOfEducation);
}
