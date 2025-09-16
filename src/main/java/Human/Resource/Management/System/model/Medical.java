


package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "medical_information")
public class Medical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diagnosisLevel;
    private String testCenter;
    private String referID;

    private String documentPath; // Path to the stored document

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private EmployeeBasicInformation employeeBasicInformation;
}
