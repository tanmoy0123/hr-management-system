package Human.Resource.Management.System.repository;

import Human.Resource.Management.System.model.PlaceofPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceofPostingRepository extends JpaRepository<PlaceofPosting, Long> {
}
