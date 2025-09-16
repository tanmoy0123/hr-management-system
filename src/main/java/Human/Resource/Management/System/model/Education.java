package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String levelOfEducation; // Matches the levelOfEducation dropdown
    private String examDegreeTitle; // Matches the examDegreeTitle dropdown

    private String boardUniversity; // Matches the Board/University field

    private String documentPath; // For document file uploads
    private String schoolCollege; // For School Coll

    //06-02-2025
    private String passingYear; // Matches the Passing Year field
    private String groupName; // Matches the Group Name field
    private String resultType; // Matches the Result Type field
    private String marks; // Matches the marks field
    private String gpa; // Matches the gpa field
    private String cgpa; // Matches the cgpa field
    //06-02-2025

    //17-03-2025
    private String rollNo;
    private String regNo;


    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id") // Adjusted to map correctly with the Employee ID
    private EmployeeBasicInformation employeeBasicInformation;
}
