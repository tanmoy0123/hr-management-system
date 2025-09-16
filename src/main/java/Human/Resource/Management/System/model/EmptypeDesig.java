package Human.Resource.Management.System.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.autoconfigure.web.WebProperties;


@Data
@Entity
@Table(name = "emp_type_desig")
@NoArgsConstructor
@AllArgsConstructor
public class EmptypeDesig {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "emptype_id")
	private EmpType empType;

	@ManyToOne
	@JoinColumn(name = "desg_id")
	private Designation designation;
}
