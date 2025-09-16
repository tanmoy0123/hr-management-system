package Human.Resource.Management.System.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "university_lookup")
public class UniversityLookup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "university_name", nullable = false)
	private String universityName;

	@Column(name = "university_code", nullable = false, unique = true)
	private String universityCode;

	@Column(name = "board_name", nullable = false)
	private String boardName;

	@Column(name = "board_code", nullable = false, unique = true)
	private String boardCode;

	@Column(name = "inst_name", nullable = false)
	private String instName;

	@Column(name = "inst_code", nullable = false, unique = true)
	private String instCode;

	@Column(name = "school_name", nullable = false)
	private String schoolName;

	@Column(name = "school_code", nullable = false, unique = true)
	private String schoolCode;

	@Column(name = "college_name", nullable = false)
	private String collegeName;

	@Column(name = "college_code", nullable = false, unique = true)
	private String collegeCode;


}
