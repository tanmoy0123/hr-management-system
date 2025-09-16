
package Human.Resource.Management.System.model;

import jakarta.persistence.*;
		import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "designation")
public class Designation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String designations;
	private String activeStatus;

}
