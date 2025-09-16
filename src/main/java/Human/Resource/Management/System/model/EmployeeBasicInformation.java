
package Human.Resource.Management.System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee_basic_information")
public class EmployeeBasicInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String firstName;
    @Column(nullable = false, unique = true)
    private String lastName;
    private String familyName;
    private String fathersName;
    private String mothersName;
    private String dob;
    private String religion;
    private String gender;
    private String bloodGroup;
    private String nationality;
    private String maritalStatus;
    private String jobType;
    private String designation;
    private Integer rank;

    @Column(name = "employee_id", unique = true, nullable = false)
    private Integer employeeId;

    //URL for uploaded photo
    private String photoUrl;


    //@Column(name = "CREATED_OR_UPDATED_ON")
    private LocalDateTime createdOrUpdatedOn;

    //@Column(name = "CREATED_OR_UPDATED_BY")
    private String createdOrUpdatedBy;


    @Column(name = "active_status")
    private Integer activeStatus; // Use Integer to directly store 1 or 0

    //@Column(name = "DEACTIVATED_ON")
    private LocalDateTime deactivatedOn;

    //@Column(name = "DEACTIVATED_BY")
    private String deactivatedBy;



    @JsonIgnore
    @OneToMany(mappedBy = "employeeBasicInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Education> educationList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "employeeBasicInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PreviousExperience> previousExperienceList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "employeeBasicInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Family> familyList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "employeeBasicInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Medical> medicalList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "employeeBasicInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PoliceClearance> policeClearanceList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "employeeBasicInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Address> AddressList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "employeeBasicInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ContactInformation> ContactInformationList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "employeeBasicInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PlaceofPosting> placeofPostingList = new ArrayList<>();

}
