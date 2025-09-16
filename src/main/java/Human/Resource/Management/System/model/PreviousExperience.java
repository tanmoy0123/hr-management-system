package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "previous_experience")
public class PreviousExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String organizationName;
//    private String designation;

    private String companyBusiness;
    private String companyBusiness2;
    private String companyName;
    private String areaDivision;
    private String areaDivision2;
    private String deptFunction;
    private String branchLocation;
    private String areaExpertise;
    private String designation;
    private String jobResponsibilities;
    private String startDate;
    private String endDate;

    private String documentPath;


    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private EmployeeBasicInformation employeeBasicInformation;
}
