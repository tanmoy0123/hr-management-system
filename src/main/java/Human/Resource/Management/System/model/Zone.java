package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "zone")
@NoArgsConstructor
@AllArgsConstructor


public class Zone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String zoneName;


	private String zoneCode;

	private String zoneLocation;

	private String activeStatus;



}
