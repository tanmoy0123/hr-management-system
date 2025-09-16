package Human.Resource.Management.System.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Table(name = "location_lookup")
public class LocationLookup {
	@Id
	private String id;
	private String countryName;
	private String countryCode;
	private String divisionName;
	private String divisionCode;
	private String districtName;
	private String districtCode;
	private String policeStationName;
	private String policeStationCode;
	private String postOfficeName;
	private String postOfficeCode;
}
