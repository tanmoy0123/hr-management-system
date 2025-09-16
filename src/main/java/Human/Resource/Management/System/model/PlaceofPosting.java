package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "place_posting")
public class PlaceofPosting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String employeeName;

	private String wingName;
	private String wingCode;
	private String wingLocation;

	private String divisionName;
	private String divisionCode;
	private String divisionLocation;

	private String zoneName;
	private String zoneCode;
	private String zoneLocation;

	private String branchName;
	private String branchCode;
	private String branchLocation;

	private String designation;
	private String placeofPostingLocation;


	@ManyToOne
	@JoinColumn(name = "wing_division_id")
	private WingDivision wingDivision;

	@ManyToOne
	@JoinColumn(name = "zone_branch_id")
	private ZoneBranch zoneBranch;


	@ManyToOne
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
	private EmployeeBasicInformation employeeBasicInformation;
}
