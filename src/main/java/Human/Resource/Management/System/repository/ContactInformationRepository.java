package Human.Resource.Management.System.repository;


import Human.Resource.Management.System.model.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInformationRepository extends JpaRepository<ContactInformation, Long> {
	boolean existsByRefId(String refId);
	boolean existsByEmail(String email);
	boolean existsByBusnEmail(String email);
	boolean existsByPhNum(String phNum);
	boolean existsByAltphNum(String altNum);
}
