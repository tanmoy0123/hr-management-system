package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "test_center_lookup")
public class TestCenterLookup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String testCenterName;
	private String testCenterCode;
}
