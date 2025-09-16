package Human.Resource.Management.System.repository;


import Human.Resource.Management.System.model.UniversityLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UniversityLookupRepository extends JpaRepository<UniversityLookup, Long> {
	@Query("SELECT u.schoolName, u.schoolCode FROM UniversityLookup u WHERE u.boardName = :boardName")
	List<Object[]> findSchoolsByBoard(String boardName);

	@Query("SELECT u.collegeName, u.collegeCode FROM UniversityLookup u WHERE u.boardName = :boardName")
	List<Object[]> findCollegesByBoard(String boardName);
}
