package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fields for Permanent Address Details
    @Column(name = "prm_country")
    private String prmCountry;

    @Column(name = "prm_div")
    private String prmDiv;

    @Column(name = "prm_dist")
    private String prmDist;

    @Column(name = "prm_ps")
    private String prmPS;

    @Column(name = "prm_po")
    private String prmPO;

    @Column(name = "prm_addr")
    private String prmAddr;



    // Fields for Present Address Details
    @Column(name = "prs_country")
    private String prsCountry;

    @Column(name = "prs_div")
    private String prsDiv;

    @Column(name = "prs_dist")
    private String prsDist;

    @Column(name = "prs_ps")
    private String prsPS;

    @Column(name = "prs_po")
    private String prsPO;

    @Column(name = "prs_addr")
    private String prsAddr;


    // Fields for Contact Address Details
    @Column(name = "con_country")
    private String conCountry;

    @Column(name = "con_div")
    private String conDiv;

    @Column(name = "con_dist")
    private String conDist;

    @Column(name = "con_ps")
    private String conPS;

    @Column(name = "con_po")
    private String conPO;

    @Column(name = "con_addr")
    private String conAddr;



    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id") // Foreign key column linking to EmployeeBasicInformation
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private EmployeeBasicInformation employeeBasicInformation;

}


