package Human.Resource.Management.System.service;

import Human.Resource.Management.System.model.Division;
import Human.Resource.Management.System.model.Wing;
import Human.Resource.Management.System.model.WingDivision;

import java.util.List;

/**
 * Service interface for managing Wings, Divisions, and the mapping between them.
 */
public interface WingDivisionService {


	List<Wing> getAllWings();
	List<Division> getAllDivisions();
	void saveWing(Wing wing);
	void saveDivision(Division division);
	List<WingDivision> getAllWingWiseDivisions(); // Ensure method name matches
	void saveWingDivision(Long wingId, Long divisionId);

	//15-02-2025
	List<WingDivision> findByWingName(String wingName);
	void deleteWingDivisionById(Long id);
	void updateWIngDivision(Long id, String wingName, String divisionName);
	WingDivision getWingDivision(Long id);
	//15-02-2025


	//18-02-2025
	//Wing Update and Delete
	void updateWing(Long id, Wing wing);
	void deleteWingById(Long id);

	//Division Update and Delete
	void updateDivision(Long id, Division division);
	void deleteDivisionById(Long id);
	//18-02-2025

}
