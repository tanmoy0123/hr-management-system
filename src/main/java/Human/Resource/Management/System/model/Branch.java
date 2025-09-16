package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branch")
public class Branch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String branchName;
	private String branchCode;
	private String branchLocation;
	private String activeStatus;
}


