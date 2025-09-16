

package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "family_information")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String relationType;
    private String relationCode;
    private String refId;
    private String name;
    private String gender;
    private Integer age;
    private String dateOfBirth;
    private Integer alive;
    private String brcPath;
    private String nidPath;
    private String marrigeCertificatePath;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private EmployeeBasicInformation employeeBasicInformation;
}

