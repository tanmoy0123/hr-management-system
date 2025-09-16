package Human.Resource.Management.System.serviceImpl;

import Human.Resource.Management.System.model.*;
import Human.Resource.Management.System.repository.*;
import Human.Resource.Management.System.service.WingDivisionService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WingDivisionServiceImpl implements WingDivisionService {

	private final WingRepository wingRepository;
	private final DivisionRepository divisionRepository;
	private final WingDivisionRepository wingDivisionRepository;
	private final ZoneBranchRepository zoneBranchRepository;
	private final EmployeeBasicInformationRepository employeeBasicInformationRepository;

	public WingDivisionServiceImpl(WingRepository wingRepository, DivisionRepository divisionRepository, WingDivisionRepository wingDivisionRepository, ZoneBranchRepository zoneBranchRepository, EmployeeBasicInformationRepository employeeBasicInformationRepository) {
		this.wingRepository = wingRepository;
		this.divisionRepository = divisionRepository;
		this.wingDivisionRepository = wingDivisionRepository;
		this.zoneBranchRepository = zoneBranchRepository;
		this.employeeBasicInformationRepository = employeeBasicInformationRepository;
	}

	@Override
	public List<Wing> getAllWings() {
		return wingRepository.findAll();
	}

	@Override
	public void saveWing(Wing wing) {
		if (wing != null) {
			wingRepository.save(wing);
		}

	}

	@Override
	public List<Division> getAllDivisions() {
		return divisionRepository.findAll();
	}

	@Override
	public void saveDivision(Division division) {
		if (division != null) {
			divisionRepository.save(division);
		}
	}

	@Override
	public List<WingDivision> getAllWingWiseDivisions() {
		return wingDivisionRepository.findAll();
	}

	@Override
	public void saveWingDivision(Long wingId, Long divisionId) {
		Optional<Wing> wing = wingRepository.findById(wingId);
		Optional<Division> division = divisionRepository.findById(divisionId);



		if (wing.isPresent() && division.isPresent()) {
			WingDivision wingDivision = new WingDivision();
			wingDivision.setWing(wing.get());
			wingDivision.setDivision(division.get());
			wingDivisionRepository.save(wingDivision);


		} else {
			throw new IllegalArgumentException("Invalid Wing or Division ID");
		}
	}

	//15-02-2025
	@Override
	public List<WingDivision> findByWingName(String wingName) {
		return wingDivisionRepository.findWingDivisionByWing_WingName(wingName);
	}

	@Override
	public void deleteWingDivisionById(Long id) {
		wingDivisionRepository.deleteById(id);
	}

	@Override
	public void updateWIngDivision(Long id, String wingName, String divisionName) {
		WingDivision optionalWingDivision = wingDivisionRepository.findById(id).get();

		Wing wing = optionalWingDivision.getWing();
		Division division = optionalWingDivision.getDivision();

		if (!wingName.isEmpty() && !divisionName.isEmpty()){
			wing.setWingName(wingName);
			division.setDivName(divisionName);

			optionalWingDivision.setWing(wing);
			optionalWingDivision.setDivision(division);
		}
		wingDivisionRepository.save(optionalWingDivision);
	}

	@Override
	public WingDivision getWingDivision(Long id) {
		return wingDivisionRepository.findById(id).get();
	}
	//15-02-2025

	//18-02-2025
	@Override
	public void updateWing(Long id, Wing wing) {
		Wing getWingById = wingRepository.findById(id).get();
		BeanUtils.copyProperties(wing, getWingById);
		wingRepository.save(getWingById);
	}

	@Override
	public void deleteWingById(Long id) {
		wingRepository.deleteById(id);
	}

	@Override
	public void updateDivision(Long id, Division division) {
		Division getDivisionById = divisionRepository.findById(id).get();
		BeanUtils.copyProperties(division, getDivisionById);
		divisionRepository.save(getDivisionById);
	}

	@Override
	public void deleteDivisionById(Long id) {
		divisionRepository.deleteById(id);
	}
	//18-02-2025
}