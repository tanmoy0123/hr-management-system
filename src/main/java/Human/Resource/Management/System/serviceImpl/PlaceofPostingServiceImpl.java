package Human.Resource.Management.System.serviceImpl;

import Human.Resource.Management.System.model.*;
import Human.Resource.Management.System.repository.EmployeeBasicInformationRepository;
import Human.Resource.Management.System.repository.PlaceofPostingRepository;
import Human.Resource.Management.System.repository.WingDivisionRepository;
import Human.Resource.Management.System.repository.ZoneBranchRepository;
import Human.Resource.Management.System.service.PlaceofPostingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceofPostingServiceImpl implements PlaceofPostingService {

	private final WingDivisionRepository wingDivisionRepo;
	private final ZoneBranchRepository zoneBranchRepo;
	private final EmployeeBasicInformationRepository employeeBasicInformationRepo;
	private final PlaceofPostingRepository placeofPostingRepo;

	public PlaceofPostingServiceImpl(WingDivisionRepository wingDivisionRepo, ZoneBranchRepository zoneBranchRepo, EmployeeBasicInformationRepository employeeBasicInformationRepo, PlaceofPostingRepository placeofPostingRepo) {
		this.wingDivisionRepo = wingDivisionRepo;
		this.zoneBranchRepo = zoneBranchRepo;
		this.employeeBasicInformationRepo = employeeBasicInformationRepo;
		this.placeofPostingRepo = placeofPostingRepo;
	}

	@Override
	public List<PlaceofPosting> getAllPlaceofPostings() {
		return placeofPostingRepo.findAll();
	}

	@Override
	public PlaceofPosting getById(Long id) {
		return placeofPostingRepo.findById(id).get();
	}

	@Override
	public void save(EmployeeBasicInformation employee) {

		if (employee.getPlaceofPostingList() != null){
			EmployeeBasicInformation getEmployee = employeeBasicInformationRepo.findByEmployeeId(employee.getEmployeeId());
			PlaceofPosting placeofPosting = employee.getPlaceofPostingList().get(0);


			WingDivision wingDivision = wingDivisionRepo.findFirstByWing_WingName(placeofPosting.getWingName());
			ZoneBranch zoneBranch = zoneBranchRepo.findFirstByZone_ZoneName(placeofPosting.getZoneName());

			if (wingDivision == null){
				placeofPosting.setWingCode("");
				placeofPosting.setWingLocation("");

				placeofPosting.setDivisionCode("");
				placeofPosting.setDivisionLocation("");
			} else {
				Wing wing = wingDivision.getWing();
				Division division = wingDivision.getDivision();

				placeofPosting.setWingCode(wing.getWingCode());
				placeofPosting.setWingLocation(wing.getWingLocation());
				placeofPosting.setDivisionCode(division.getDivCode());
				placeofPosting.setDivisionLocation(division.getDivLocation());
				placeofPosting.setWingDivision(wingDivision);
			}

			if (zoneBranch == null){
				placeofPosting.setZoneCode("");
				placeofPosting.setZoneLocation("");
				placeofPosting.setBranchCode("");
				placeofPosting.setBranchLocation("");
			} else {
				Zone zone = zoneBranch.getZone();
				Branch branch = zoneBranch.getBranch();
				placeofPosting.setZoneCode(zone.getZoneCode());
				placeofPosting.setZoneLocation(zone.getZoneLocation());

				placeofPosting.setBranchCode(branch.getBranchCode());
				placeofPosting.setBranchLocation(branch.getBranchLocation());
				placeofPosting.setZoneBranch(zoneBranch);
			}


			placeofPosting.setEmployeeBasicInformation(getEmployee);
			placeofPostingRepo.save(placeofPosting);
		}
	}

	@Override
	public void update(Long id, PlaceofPosting placeofPosting) {
		PlaceofPosting getPoP= getById(id);

		if (placeofPosting.getWingName() != null || placeofPosting.getDivisionName() != null){
			WingDivision wingDivision = wingDivisionRepo.findFirstByWing_WingName(placeofPosting.getWingName());
			Wing wing = wingDivision.getWing();
			Division division = wingDivision.getDivision();
			getPoP.setWingCode(wing.getWingCode());
			getPoP.setWingLocation(wing.getWingLocation());
			getPoP.setDivisionCode(division.getDivCode());
			getPoP.setDivisionLocation(division.getDivLocation());
			getPoP.setWingDivision(wingDivision);
		}

		if (placeofPosting.getZoneName() != null || placeofPosting.getBranchName() != null){
			ZoneBranch zoneBranch = zoneBranchRepo.findFirstByZone_ZoneName(placeofPosting.getZoneName());
			Zone zone = zoneBranch.getZone();
			Branch branch = zoneBranch.getBranch();
			getPoP.setZoneCode(zone.getZoneCode());
			getPoP.setZoneLocation(zone.getZoneLocation());
			getPoP.setBranchCode(branch.getBranchCode());
			getPoP.setBranchLocation(branch.getBranchLocation());
			getPoP.setZoneBranch(zoneBranch);
		}


		getPoP.setEmployeeName(placeofPosting.getEmployeeName());
		getPoP.setWingName(placeofPosting.getWingName());
		getPoP.setDivisionName(placeofPosting.getDivisionName());
		getPoP.setZoneName(placeofPosting.getZoneName());
		getPoP.setBranchName(placeofPosting.getBranchName());
//		getPoP.setDesignation(placeofPosting.getDesignation());
		getPoP.setPlaceofPostingLocation(placeofPosting.getPlaceofPostingLocation());

		getPoP.setWingCode(getPoP.getWingCode());
		getPoP.setWingLocation(getPoP.getWingLocation());
		getPoP.setDivisionCode(getPoP.getDivisionCode());
		getPoP.setDivisionLocation(getPoP.getDivisionLocation());

		getPoP.setZoneCode(getPoP.getZoneCode());
		getPoP.setZoneLocation(getPoP.getZoneLocation());
		getPoP.setBranchCode(getPoP.getBranchCode());
		getPoP.setBranchLocation(getPoP.getBranchLocation());


		getPoP.setWingDivision(getPoP.getWingDivision());
		getPoP.setZoneBranch(getPoP.getZoneBranch());
		getPoP.setEmployeeBasicInformation(getPoP.getEmployeeBasicInformation());
		placeofPostingRepo.save(getPoP);
	}

	@Override
	public void delete(Long id) {
		placeofPostingRepo.deleteById(id);
	}
}
