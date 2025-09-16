package Human.Resource.Management.System.repository;




import Human.Resource.Management.System.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Integer> {
	boolean existsByRollNo(String rollNo);
	boolean existsByRegNo(String regNo);
}
