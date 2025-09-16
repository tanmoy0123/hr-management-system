package Human.Resource.Management.System.repository;

import Human.Resource.Management.System.model.LocationLookup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationLookupRepository extends JpaRepository<LocationLookup, Long> {
	List<LocationLookup> findByCountryName(String countryName);
	List<LocationLookup> findByDivisionName(String divisionName);
	List<LocationLookup> findByDistrictName(String districtName);
	List<LocationLookup> findByPoliceStationName(String thanaName);
}
