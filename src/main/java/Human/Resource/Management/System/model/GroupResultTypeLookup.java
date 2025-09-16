package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "group_resulttype_lookup")
public class GroupResultTypeLookup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String groupName;
	private String groupCode;
	private String resultType;
	private String resultTypeCode;

}
