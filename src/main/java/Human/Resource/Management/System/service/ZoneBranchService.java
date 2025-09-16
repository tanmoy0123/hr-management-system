package Human.Resource.Management.System.service;


import Human.Resource.Management.System.model.Branch;
import Human.Resource.Management.System.model.Zone;
import Human.Resource.Management.System.model.ZoneBranch;

import java.util.List;

public interface ZoneBranchService {
	// Retrieve all zones from the database
	List<Zone> getAllZones();

	// Retrieve all branches from the database
	List<Branch> getAllBranches();

	// Save a new zone to the database
	void saveZone(Zone zone);

	// Save a new branch to the database
	void saveBranch(Branch branch);

	// Retrieve all zone-branch relationships
	List<ZoneBranch> getAllZoneWiseBranch();

	// Associate a branch with a zone
	void saveZoneBranch(Long zoneId, Long branchId);

	// Find zone-branch relationships by zone name
	List<ZoneBranch> findByZoneName(String zoneName);

	// Delete a zone-branch relationship by ID
	void deleteZoneBranchById(Long id);

	// Update an existing zone-branch relationship
	void updateZoneBranch(Long id, String zoneName, String branchName);

	// Retrieve wing-division details for a specific ID
	ZoneBranch getZoneBranch(Long id);

	// Update an existing zone's details
	void updateZone(Long id, Zone zone);

	// Delete a zone by ID
	void deleteZoneById(Long id);

	// Update an existing branch's details
	void updateBranch(Long id, Branch branch);

	// Delete a branch by ID
	void deleteBranchById(Long id);
}
