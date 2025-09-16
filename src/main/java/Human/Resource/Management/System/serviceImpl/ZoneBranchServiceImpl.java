package Human.Resource.Management.System.serviceImpl;

import Human.Resource.Management.System.model.*;
import Human.Resource.Management.System.repository.BranchRepository;
import Human.Resource.Management.System.repository.WingDivisionRepository;
import Human.Resource.Management.System.repository.ZoneBranchRepository;
import Human.Resource.Management.System.repository.ZoneRepository;
import Human.Resource.Management.System.service.ZoneBranchService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZoneBranchServiceImpl implements ZoneBranchService {

	private final ZoneRepository zoneRepo;
	private final BranchRepository branchRepo;
	private final ZoneBranchRepository zoneBranchRepo;
	private final WingDivisionRepository wingDivisionRepository;

	public ZoneBranchServiceImpl(ZoneRepository zoneRepo, BranchRepository branchRepo, ZoneBranchRepository zoneBranchRepo, WingDivisionRepository wingDivisionRepository) {
		this.zoneRepo = zoneRepo;
		this.branchRepo = branchRepo;
		this.zoneBranchRepo = zoneBranchRepo;
		this.wingDivisionRepository = wingDivisionRepository;
	}


	@Override
	public List<Zone> getAllZones() {
		return zoneRepo.findAll();
	}

	@Override
	public List<Branch> getAllBranches() {
		return branchRepo.findAll();
	}

	@Override
	public void saveZone(Zone zone) {
		if (zone != null){
			zoneRepo.save(zone);
		}


	}

	@Override
	public void saveBranch(Branch branch) {
		if (branch != null){
			branchRepo.save(branch);
		}
	}

	@Override
	public List<ZoneBranch> getAllZoneWiseBranch() {
		return zoneBranchRepo.findAll();
	}

	@Override
	public void saveZoneBranch(Long zoneId, Long branchId) {
		Optional<Zone> zone = zoneRepo.findById(zoneId);
		Optional<Branch> branch = branchRepo.findById(branchId);

		if (zone.isPresent() && branch.isPresent()) {
			ZoneBranch zoneBranch = new ZoneBranch();
			zoneBranch.setZone(zone.get());
			zoneBranch.setBranch(branch.get());
			zoneBranchRepo.save(zoneBranch);
		} else {
			throw new IllegalArgumentException("Invalid Zone or Branch ID");
		}
	}

	@Override
	public List<ZoneBranch> findByZoneName(String zoneName) {
		return  zoneBranchRepo.findZoneBranchByZone_ZoneName(zoneName);
	}

	@Override
	public void deleteZoneBranchById(Long id) {
		zoneBranchRepo.deleteById(id);
	}

	@Override
	public void updateZoneBranch(Long id, String zoneName, String branchName) {

		ZoneBranch zoneBranch = zoneBranchRepo.findById(id).get();
		Zone zone = zoneBranch.getZone();
		Branch branch = zoneBranch.getBranch();
		if (!zoneName.isEmpty() && !branchName.isEmpty()){
			zone.setZoneName(zoneName);
			branch.setBranchName(branchName);

			zoneBranch.setZone(zone);
			zoneBranch.setBranch(branch);
		}
		zoneBranchRepo.save(zoneBranch);

	}

	@Override
	public ZoneBranch getZoneBranch(Long id) {
		return zoneBranchRepo.findById(id).get();
	}

	@Override
	public void updateZone(Long id, Zone zone) {
		Zone getZone = zoneRepo.findById(id).get();
		BeanUtils.copyProperties(zone, getZone);
		zoneRepo.save(getZone);
	}

	@Override
	public void deleteZoneById(Long id) {
		zoneRepo.deleteById(id);
	}

	@Override
	public void updateBranch(Long id, Branch branch) {
		Branch getBranch = branchRepo.findById(id).get();
		BeanUtils.copyProperties(branch, getBranch);
		branchRepo.save(getBranch);

	}

	@Override
	public void deleteBranchById(Long id) {
		branchRepo.deleteById(id);
	}
}
