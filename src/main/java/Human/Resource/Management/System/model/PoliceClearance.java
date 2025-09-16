package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "police_clearance")
public class PoliceClearance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String division;
    private String district;
    private String thana;

    private String clearanceDate;


    private String documentPath;
    private String referID;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private EmployeeBasicInformation employeeBasicInformation;

}








