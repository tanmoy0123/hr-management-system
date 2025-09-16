package Human.Resource.Management.System.repository;



import Human.Resource.Management.System.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByRelationType(String relationType);
}
