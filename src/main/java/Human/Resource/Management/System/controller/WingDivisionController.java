package Human.Resource.Management.System.controller;

import Human.Resource.Management.System.model.*;
import Human.Resource.Management.System.service.WingDivisionService;
import Human.Resource.Management.System.service.ZoneBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class WingDivisionController {

	@Autowired
	private WingDivisionService wingDivisionService;

	@Autowired
	private ZoneBranchService zoneBranchService;

	//Wing and Division Part Controller Start
	@GetMapping("/manage")
	public String showForm(Model model) {
		// Fetch updated list of wings, divisions, and wing-wise divisions
		List<Wing> wings = wingDivisionService.getAllWings();
		List<Division> divisions = wingDivisionService.getAllDivisions();
		List<WingDivision> wingDivisions = wingDivisionService.getAllWingWiseDivisions(); // Corrected method name

		//15-02-2025
		List<String> specificWingName = wingDivisionService.getAllWings().stream().map(Wing::getWingName).distinct().toList();
		model.addAttribute("wingsName", specificWingName);
		//15-02-2025

		// Add list of wings to the model
		model.addAttribute("wings", wings);

		// Bind form input to a new Wing object
		model.addAttribute("wing", new Wing());

		// Add list of divisions to the model
		model.addAttribute("divisions", divisions);

		// Bind form input to a new Division object
		model.addAttribute("division", new Division());

		// Add list of wing-division relationships to the model
		model.addAttribute("wingDivisions", wingDivisions);

		// Bind form input to a new WingDivision object
		model.addAttribute("wingDivision", new WingDivision());

		return "employee/wing-division";
	}


	@PostMapping("/addWing")
	public String addWing(@ModelAttribute Wing wing) {
		// Save the new wing (Log it for debugging)
		System.out.println("Saving Wing: " + wing);
		wingDivisionService.saveWing(wing);
		return "redirect:/admin/manage";  // Redirect to manage page to show updated list
	}

	@PostMapping("/addDivision")
	public String addDivision(@ModelAttribute Division division) {
		// Save the new division (Log it for debugging)
		System.out.println("Saving Division: " + division);
		wingDivisionService.saveDivision(division);
		return "redirect:/admin/manage";  // Redirect to manage page to show updated list
	}

	// Other existing methods...

	@PostMapping("/mapWingDivision")
	public String mapWingDivision(@RequestParam Long wingId, @RequestParam Long divisionId) {
		wingDivisionService.saveWingDivision(wingId, divisionId);
		return "redirect:/admin/manage";
	}

	//15-02-2025
	//Find division by wing name from Wing-Division
	@GetMapping("/findDivisionByWingNameFromWingDivision/{wingName}")
	public ResponseEntity<List<WingDivision>> getWingDivisionByWingName(@PathVariable String wingName) {
		// Calling the service to find WingDivision by the Wing name
		return ResponseEntity.ok(wingDivisionService.findByWingName(wingName));
	}

	//Delete Wing Division by id
	@GetMapping("/deleteWingDivision/{id}")
	public String deleteWingDivisionById(@PathVariable Long id) {
		wingDivisionService.deleteWingDivisionById(id);
		return "redirect:/admin/manage";
	}

	//Update Wing Division by id
	@GetMapping("/updateWingDivision/{id}")
	public ResponseEntity<WingDivision> updateWingDivision(@PathVariable Long id) {
		WingDivision wdUpdate = wingDivisionService.getWingDivision(id);
		System.out.println(wdUpdate);
		return ResponseEntity.ok(wdUpdate);
	}

	//Update Wing Division by id
	@PostMapping("/updateWingDivision/{id}")
	public String updateWingDivisionById(
			@PathVariable Long id,
			@RequestParam String wingName,
			@RequestParam String divisionName
	) {
		wingDivisionService.updateWIngDivision(id, wingName, divisionName);
		return "redirect:/admin/manage";
	}
	//15-02-2025


	//18-02-2025

	//Update and Delete Wing
	@PostMapping("/updateWingById/{id}")
	public String updateWingById(@PathVariable Long id, @ModelAttribute Wing wing) {
		wingDivisionService.updateWing(id, wing);
		return "redirect:/admin/manage";
	}

	@GetMapping("/deleteWingById/{id}")
	public String deleteWingById(@PathVariable Long id) {
		wingDivisionService.deleteWingById(id);
		return "redirect:/admin/manage";
	}

	//Update and Delete Division
	@PostMapping("/updateDivisionById/{id}")
	public String updateDivisionById(@PathVariable Long id, @ModelAttribute Division division) {
		wingDivisionService.updateDivision(id, division);
		return "redirect:/admin/manage";
	}

	@GetMapping("/deleteDivisionById/{id}")
	public String deleteDivisionById(@PathVariable Long id) {
		wingDivisionService.deleteDivisionById(id);
		return "redirect:/admin/manage";
	}
	//18-02-2025
	//Wing and Division Part Controller End



}


