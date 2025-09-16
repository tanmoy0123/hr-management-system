package Human.Resource.Management.System.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.autoconfigure.web.WebProperties;


@Data
@Entity
@Table(name = "wing_division")
@NoArgsConstructor
@AllArgsConstructor

public class WingDivision {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "wing_id")
	private Wing wing;

	@ManyToOne
	@JoinColumn(name = "division_id")
	private Division division;
}
