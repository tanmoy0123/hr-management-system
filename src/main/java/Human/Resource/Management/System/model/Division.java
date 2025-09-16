package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "division")
public class Division {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String divName;

	private String divCode;

	private String divLocation;

	private String activeStatus;

}


