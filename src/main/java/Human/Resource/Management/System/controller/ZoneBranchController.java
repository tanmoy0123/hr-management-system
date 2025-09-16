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
public class ZoneBranchController {




	private  final ZoneBranchService zoneBranchService;

	public ZoneBranchController(ZoneBranchService zoneBranchService) {
		this.zoneBranchService = zoneBranchService;
	}


	//Zone and Branch Part Controller Start
//19-02-2025
	@GetMapping("/manage-zone-wise-branch")
	public String showFormZoneWiseBranch(Model model) {
		// Retrieve all zones
		List<Zone> zones = zoneBranchService.getAllZones();

		// Retrieve all branches
		List<Branch> branches = zoneBranchService.getAllBranches();

		// Retrieve all zone-branch relationships
		List<ZoneBranch> zoneBranches = zoneBranchService.getAllZoneWiseBranch(); // Corrected method name

		// Get distinct zone names for dropdown selection
		List<String> specificZoneNames = zoneBranchService.getAllZones().stream()
				.map(Zone::getZoneName)
				.distinct()
				.toList();
		model.addAttribute("zonesName", specificZoneNames);

		// Add zones to the model
		model.addAttribute("zones", zones);

		// Bind form input to a new Zone object
		model.addAttribute("zone", new Zone());

		// Add branches to the model
		model.addAttribute("branches", branches);

		// Bind form input to a new Branch object
		model.addAttribute("branch", new Branch());

		// Add zone-branch relationships to the model
		model.addAttribute("zoneBranches", zoneBranches);

		// Bind form input to a new ZoneBranch object
		model.addAttribute("zoneBranch", new ZoneBranch());

		return "employee/zone-branch";
	}

	@PostMapping("/addZone")
	public String addZone(@ModelAttribute Zone zone) {
		// Save the new zone (Log it for debugging)
		System.out.println("Saving Zone: " + zone);
		zoneBranchService.saveZone(zone);
		return "redirect:/admin/manage-zone-wise-branch";  // Redirect to updated page
	}

	@PostMapping("/addBranch")
	public String addBranch(@ModelAttribute Branch branch) {
		// Save the new branch (Log it for debugging)
		System.out.println("Saving Branch: " + branch);
		zoneBranchService.saveBranch(branch);
		return "redirect:/admin/manage-zone-wise-branch";  // Redirect to updated page
	}

	@PostMapping("/mapZoneBranch")
	public String mapZoneBranch(@RequestParam Long zoneId, @RequestParam Long branchId) {
		// Save the zone-branch relationship
		zoneBranchService.saveZoneBranch(zoneId, branchId);
		return "redirect:/admin/manage-zone-wise-branch";
	}

	// Find branch by zone name
	@GetMapping("/findByZoneName/{zoneName}")
	public ResponseEntity<List<ZoneBranch>> getZoneBranchByZoneName(@PathVariable String zoneName) {
		// Retrieve ZoneBranch entries for the given zone name
		return ResponseEntity.ok(zoneBranchService.findByZoneName(zoneName));
	}

	// Delete ZoneBranch by ID
	@GetMapping("/deleteZoneBranch/{id}")
	public String deleteZoneBranchById(@PathVariable Long id) {
		// Remove ZoneBranch entry
		zoneBranchService.deleteZoneBranchById(id);
		return "redirect:/admin/manage-zone-wise-branch";
	}

	// Get ZoneBranch details by ID for update
	@GetMapping("/updateZoneBranch/{id}")
	public ResponseEntity<ZoneBranch> updateZoneBranch(@PathVariable Long id) {
		ZoneBranch zbUpdate = zoneBranchService.getZoneBranch(id);
		System.out.println(zbUpdate);
		return ResponseEntity.ok(zbUpdate);
	}

	// Update ZoneBranch details by ID
	@PostMapping("/updateZoneBranch/{id}")
	public String updateZoneBranchById(@PathVariable Long id, @RequestParam String zoneName,
									   @RequestParam String branchName) {
		zoneBranchService.updateZoneBranch(id, zoneName, branchName);
		return "redirect:/admin/manage-zone-wise-branch";
	}

	// Update Zone details by ID
	@PostMapping("/updateZoneById/{id}")
	public String updateZoneById(@PathVariable Long id, @ModelAttribute Zone zone) {
		zoneBranchService.updateZone(id, zone);
		return "redirect:/admin/manage-zone-wise-branch";
	}

	// Delete Zone by ID
	@GetMapping("/deleteZoneById/{id}")
	public String deleteZoneById(@PathVariable Long id) {
		zoneBranchService.deleteZoneById(id);
		return "redirect:/admin/manage-zone-wise-branch";
	}

	// Update Branch details by ID
	@PostMapping("/updateBranchById/{id}")
	public String updateBranchById(@PathVariable Long id, @ModelAttribute Branch branch) {
		zoneBranchService.updateBranch(id, branch);
		return "redirect:/admin/manage-zone-wise-branch";
	}

	// Delete Branch by ID
	@GetMapping("/deleteBranchById/{id}")
	public String deleteBranchById(@PathVariable Long id) {
		zoneBranchService.deleteBranchById(id);
		return "redirect:/admin/manage-zone-wise-branch";
	}

	//19-02-2025
	//Zone and Branch Part Controller End


}


