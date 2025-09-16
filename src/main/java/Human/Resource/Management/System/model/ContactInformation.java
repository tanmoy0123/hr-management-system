

package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "contact_information")
public class ContactInformation {

    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "id_type", nullable = false, length = 50)
    private String idType;
    @Column(name = "reference_id", nullable = false, length = 100)
    private String refId;
    @Column(name = "reference_documents")
    private byte[] refDocs;
    @Column(name = "issue_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issDate;
    @Column(name = "issue_country", length = 50)
    private String issCountry;
    @Column(name = "issue_place", length = 100)
    private String issPlace;
    @Column(name = "expiry_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expDate;
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    @Column(name = "busn_email", nullable = false, unique = true, length = 100)
    private String busnEmail;
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phNum;
    @Column(name = "alternate_phone_number", length = 15)
    private String altphNum;
    @Column(name = "social_media_link", length = 255)
    private String smediaLink;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id") // Foreign key column linking to EmployeeBasicInformation
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private EmployeeBasicInformation employeeBasicInformation;
}

