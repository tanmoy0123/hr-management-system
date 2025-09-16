package Human.Resource.Management.System.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.autoconfigure.web.WebProperties;


@Data
@Entity
@Table(name = "zone_branch")
@NoArgsConstructor
@AllArgsConstructor
public class ZoneBranch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "zone_id")
	private Zone zone;

	@ManyToOne
	@JoinColumn(name = "branch_id")
	private Branch branch;
}
