package Human.Resource.Management.System.controller;

import Human.Resource.Management.System.model.*;
import Human.Resource.Management.System.service.PlaceofPostingService;
import Human.Resource.Management.System.service.WingDivisionService;
import Human.Resource.Management.System.service.ZoneBranchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/place-of-posting")
public class PlaceofPostingController {

	// Add a new field to hold the service object
	private final PlaceofPostingService placeofPostingService;

	private final WingDivisionService wingDivisionService;

	private final ZoneBranchService zoneBranchService;


	public PlaceofPostingController(PlaceofPostingService placeofPostingService, WingDivisionService wingDivisionService, ZoneBranchService zoneBranchService) {
		this.placeofPostingService = placeofPostingService;
		this.wingDivisionService = wingDivisionService;
		this.zoneBranchService = zoneBranchService;
	}

	// Add a new method to show the form
	@GetMapping("/manage")
	public String showForm(Model model) {
		List<PlaceofPosting> placeofPostings = placeofPostingService.getAllPlaceofPostings();
		List<Wing> wings = wingDivisionService.getAllWings();
		List<Division> divisions = wingDivisionService.getAllDivisions();
		List<Zone> zones = zoneBranchService.getAllZones();
		List<Branch> branches = zoneBranchService.getAllBranches();

		EmployeeBasicInformation employee = new EmployeeBasicInformation();
		employee.setPlaceofPostingList(List.of(new PlaceofPosting()));


		model.addAttribute("employee", employee);
		model.addAttribute("wings", wings);
		model.addAttribute("divisions", divisions);
		model.addAttribute("zones", zones);
		model.addAttribute("branches", branches);
		model.addAttribute("placeofPostings", placeofPostings);
		return "employee/place-of-posting";
	}

// Add a new method to save the place of posting
	@PostMapping("/save")
	public String savePlaceofPosting(EmployeeBasicInformation employee) {
		placeofPostingService.save(employee);
		return "redirect:/place-of-posting/manage";
	}

	// Add a new method to delete the place of posting
	@GetMapping("/delete/{id}")
	public String deletePlaceofPosting(@PathVariable Long id) {
		placeofPostingService.delete(id);
		return "redirect:/place-of-posting/manage";
	}

	// Add a new method to update the place of posting
	@PostMapping("/update/{id}")
	public String updatePlaceofPosting(@PathVariable Long id, PlaceofPosting placeofPosting) {
		placeofPostingService.update(id, placeofPosting);
		return "redirect:/place-of-posting/manage";
	}
}
