




package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qualifications")
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Qualification name (e.g., MBA, BSc)

    @Column(nullable = false, unique = true)
    private String categoryCode; // Unique code for each qualification (e.g., MBA001, BSC001)

    private String description; // Optional description for the qualification
}
