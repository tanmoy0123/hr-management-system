package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "education_lookup")
public class EducationLookUp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "level_of_education", nullable = false)
	private String levelOfEducation;

	@Column(name = "exam_title", nullable = false)
	private String examTitle;

	//31-01-25
	@ManyToOne
	@JoinColumn(name = "university_lookup_id", nullable = false, referencedColumnName = "id")
	 private UniversityLookup universityLookup;
}
