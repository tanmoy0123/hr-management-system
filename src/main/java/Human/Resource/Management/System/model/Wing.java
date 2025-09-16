package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "wing")
@NoArgsConstructor
@AllArgsConstructor


public class Wing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String wingName;


	private String wingCode;

	private String wingLocation;

	private String activeStatus;



}
