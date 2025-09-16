package Human.Resource.Management.System.controller;
// Import necessary packages and dependencies


import Human.Resource.Management.System.model.*;
import Human.Resource.Management.System.service.EmployeeBasicInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller  // Marks this class as a Spring MVC controller
@RequestMapping("employee")  // Maps requests starting with /employee
public class EmployeeController {
   private final EmployeeBasicInformationService employeeBasicInformationService;

    public EmployeeController(EmployeeBasicInformationService employeeBasicInformationService) {
        this.employeeBasicInformationService = employeeBasicInformationService;
    }


    @GetMapping  // Handles HTTP GET requests to /employee
    public String employeeHome(Model model) {
        // Retrieves all employee records and adds them to the model
        List<EmployeeBasicInformation> allEmployee = employeeBasicInformationService.allEmployee();
        model.addAttribute("allEmployee", allEmployee);

        List<String> educationLookUps = employeeBasicInformationService.getAllEducationLevels().stream()
                .map(EducationLookUp:: getLevelOfEducation)
                .distinct()
                .toList();

        System.out.println(educationLookUps);

        List<EducationLookUp> examTitles = employeeBasicInformationService.getExamTitlesByLevel("JSC/8 Pass");
        System.out.println(examTitles);

        return "employee/index";  // Returns the view name
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/create")  // Handles GET requests to /employee/create
    public String addEmployee(Model model) {
        EmployeeBasicInformation employee = new EmployeeBasicInformation();

        // Initialize employee-related lists with default values
        employee.setContactInformationList(List.of(new ContactInformation()));
        employee.setEducationList(List.of(new Education()));
        employee.setFamilyList(List.of(new Family()));
        employee.setMedicalList(List.of(new Medical()));
        employee.setPreviousExperienceList(List.of(new PreviousExperience()));
        employee.setAddressList(List.of(new Address()));
        employee.setPoliceClearanceList(List.of(new PoliceClearance()));

        // Retrieves all categories from the database
        List<Category> categories = employeeBasicInformationService.getAllCategories();

        //Retrieves all universities from the database
        List<UniversityLookup> universities = employeeBasicInformationService.getAllUniversities();

        //17-03-2025
        //Retrieves all test center from the database
        List<TestCenterLookup> testCenters = employeeBasicInformationService.findAllTestCenter();

        // Adds categories to the model
        model.addAttribute("categories", categories);
        // Adds universities to the model
        model.addAttribute("universities", universities);
        // Adds employee to the model
        model.addAttribute("employee", employee);

        //17-03-2025
        // Adds test centers to the model
        model.addAttribute("testCenters", testCenters);

        return "employee/create";  // Returns the view name
    }

    @PostMapping("/save")  // Handles HTTP POST requests to /employee/save
    public String saveEmployee(
            @ModelAttribute("employee") EmployeeBasicInformation employee,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "contactInformationRefDocs", required = false) MultipartFile contactInformationRefDocs,
            @RequestParam(value = "brcDocument", required = false) MultipartFile brcDocument,
            @RequestParam(value = "marriageCertificateDocument", required = false) MultipartFile marriageCertificateDocument,
            @RequestParam(value = "nidDocumentOfRelationType", required = false) MultipartFile nidDocumentOfRelationType,
            @RequestParam(value = "uploadPoliceClearanceDocuments", required = false) List<MultipartFile> uploadPoliceClearanceDocuments,
            @RequestParam(value = "uploadEducationDocuments", required = false) List<MultipartFile> uploadEducationDocuments,
            @RequestParam(value = "uploadExperienceDocuments", required = false) List<MultipartFile> uploadExperienceDocuments,
            @RequestParam(value = "uploadMedicalDocuments", required = false) List<MultipartFile> uploadMedicalDocuments,
            @RequestParam(value = "isAlive", required = false) boolean isAlive,
            @RequestParam(value = "marked", required = false) boolean marked,
            @RequestParam(value = "marked2", required = false) boolean marked2,
            Model model) {
        try {
            // Debugging information
            System.out.println("Marked: " + marked);
            System.out.println("Marked2: " + marked2);
            System.out.println("isAlive: " + isAlive);

            // Save employee information using the service
            employeeBasicInformationService.saveEmployeeBasicInformation(employee, photo, contactInformationRefDocs,
                    brcDocument, marriageCertificateDocument, nidDocumentOfRelationType, uploadEducationDocuments,
                    uploadExperienceDocuments, uploadMedicalDocuments, uploadPoliceClearanceDocuments, marked,
                    marked2, isAlive);
            return "redirect:/employee/create";  // Redirect to employee creation page
        } catch (Exception e) {
            model.addAttribute("error", "Failed to save employee information. Please try again.");
            return "employee/create";  // Return to the form in case of error
        }
    }


    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        // Retrieve employee details based on the provided ID
        EmployeeBasicInformation employee = employeeBasicInformationService.getEmployeeBasicInformation(id);

        // Set various lists to ensure they are properly initialized
        employee.setContactInformationList(employee.getContactInformationList());
        employee.setEducationList(employee.getEducationList());
        employee.setFamilyList(employee.getFamilyList());
        employee.setMedicalList(employee.getMedicalList());
        employee.setPreviousExperienceList(employee.getPreviousExperienceList());
        employee.setEducationList(employee.getEducationList());
        employee.setAddressList(employee.getAddressList());
        employee.setPoliceClearanceList(employee.getPoliceClearanceList());

        // Check if the first address in the list is marked (permanent and present addresses match)
        boolean marked = checkAddressMarked(employee.getAddressList().get(0));

        // Check if the first family member is alive (assuming 1 means deceased)
        int isAlive = employee.getFamilyList().get(0).getAlive();

        System.out.println("isAlive:" + isAlive);

        // Add attributes to the model based on the family member's alive status
        if (isAlive == 1) {
            model.addAttribute("isAlive", true);
            model.addAttribute("isChecked", "checked");
        } else {
            model.addAttribute("isAlive", false);
            model.addAttribute("isChecked", "");
        }

        // Check if contact information contains reference documents and encode them to Base64
        if (employee.getContactInformationList().get(0).getRefDocs() != null
                && employee.getContactInformationList().get(0).getRefDocs().length > 0) {
            String base64Nid = Base64.getEncoder().encodeToString(employee.getContactInformationList().get(0).getRefDocs());
            model.addAttribute("contactInformationRefDocs", base64Nid);
            System.out.println(base64Nid);
        } else {
            model.addAttribute("contactInformationRefDocs", null);
        }

        // Retrieves all categories from the database
        List<Category> categories =employeeBasicInformationService.getAllCategories();

        //Retrieves all universities from the database
        List<UniversityLookup> universities = employeeBasicInformationService.getAllUniversities();

        //Retrieves all education levels from the database
        List<String> educationLevels = employeeBasicInformationService.getAllEducationLevels().stream()
                .map(EducationLookUp :: getLevelOfEducation)
                .distinct()
                .toList();

        // Adds education levels to the model
        model.addAttribute("educationLevels", educationLevels);
        // Adds categories to the model
        model.addAttribute("categories", categories);
        // Add employee details and address marked status to the model
        model.addAttribute("employee", employee);
        //Add marked status to the model
        model.addAttribute("marked", marked);
        // Adds universities to the model
        model.addAttribute("universities", universities);


        // Return the update view
        return "employee/update";
    }

    // Helper method to check if present and permanent addresses match
    private boolean checkAddressMarked(Address address) {
        if (address != null) {
            return (Objects.equals(address.getPrsAddr(), address.getPrmAddr())) &&
                    (Objects.equals(address.getPrsDist(), address.getPrmDist())) &&
                    (Objects.equals(address.getPrsCountry(), address.getPrmCountry())) &&
                    (Objects.equals(address.getPrsPS(), address.getPrmPS())) &&
                    (Objects.equals(address.getPrsPO(), address.getPrmPO())) &&
                    (Objects.equals(address.getPrsDiv(), address.getPrmDiv()));
        }
        return false;
    }


    @PostMapping("/update/{id}")  // Handles HTTP POST requests to update employee details
    public String updateEmployee(
            @PathVariable("id") int id,
            @ModelAttribute("employee") EmployeeBasicInformation updatedEmployee,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "nidDocumentOfRelationType", required = false) MultipartFile nidDocumentOfRelationType,
            @RequestParam(value = "contactInformationRefDocs", required = false) MultipartFile contactInformationRefDocs,
            @RequestParam(value = "brcDocument", required = false) MultipartFile brcDocument,
            @RequestParam(value = "marriageCertificateDocument", required = false) MultipartFile marriageCertificateDocument,
            @RequestParam(value = "uploadEducationDocuments", required = false) List<MultipartFile> uploadEducationDocuments,
            @RequestParam(value = "uploadExperienceDocuments", required = false) List<MultipartFile> uploadExperienceDocuments,
            @RequestParam(value = "uploadMedicalDocuments", required = false) List<MultipartFile> uploadMedicalDocuments,
            @RequestParam(value = "uploadPoliceClearanceDocuments", required = false) List<MultipartFile> uploadPoliceClearanceDocuments,
            @RequestParam(value = "marked", required = false) boolean marked,
            @RequestParam(value = "marked2", required = false) boolean marked2,
            @RequestParam(value = "isAlive", required = false) boolean isAlive,
            Model model) {
        try {
            // Update employee information using the service
            employeeBasicInformationService.updateEmployeeBasicInformation(id, updatedEmployee, photo, contactInformationRefDocs, brcDocument, marriageCertificateDocument, nidDocumentOfRelationType, uploadEducationDocuments, uploadExperienceDocuments, uploadMedicalDocuments, uploadPoliceClearanceDocuments, marked, marked2, isAlive);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to save employee information. Please try again.");
            return "redirect:/update/{id}";  // Redirect back to update page in case of error
        }
        return "redirect:/employee";  // Redirect to employee listing page after successful update
    }

    //Fetch All Education
    @GetMapping("/levels")
    public ResponseEntity<List<String>> getAllEducationLevel(){
        return ResponseEntity.ok(employeeBasicInformationService.getAllEducationLevels()
                .stream()
                .map(EducationLookUp :: getLevelOfEducation)
                .distinct()
                .toList());
    }

    //Fetch Exam Title For specific Education Level
    @GetMapping("/titles")
    public ResponseEntity<List<String>> getExamTitles(@RequestParam String levelOfEducation){
        return  ResponseEntity.ok(employeeBasicInformationService.getExamTitlesByLevel(levelOfEducation)
                .stream()
                .map(EducationLookUp ::  getExamTitle)
                .toList());
    }

    //Fetch  All University
    @GetMapping("/university")
    public ResponseEntity<List<UniversityLookup>> getAllUniversity(){
        return ResponseEntity.ok(employeeBasicInformationService.getAllUniversities());
    }

    //31-01-25
    //Fetch School
    @GetMapping("/schools")
    public ResponseEntity<List<Object[]>> getSchoolsByBoard(@RequestParam String boardName) {
        return ResponseEntity.ok(employeeBasicInformationService.getSchoolsByBoard(boardName));
    }

    //Fetch College
    @GetMapping("/colleges")
    public ResponseEntity<List<Object[]>> getCollegesByBoard(@RequestParam String boardName) {
        return ResponseEntity.ok(employeeBasicInformationService.getCollegesByBoard(boardName));
    }


    //06-02-2025
    //Fetch Group
    @GetMapping("/group-name")
    public ResponseEntity<List<String>> getGroupName(){
        return ResponseEntity.ok(employeeBasicInformationService.getGroupResultType()
                .stream()
                .map(GroupResultTypeLookup :: getGroupName)
                .distinct()
                .toList());
    }


    //Fetch Result Type
    @GetMapping("/result-type")
    public ResponseEntity<List<String>> getResultType(){
        return ResponseEntity.ok(employeeBasicInformationService.getGroupResultType()
                .stream().map(GroupResultTypeLookup :: getResultType).distinct().toList());
    }
    //06-02-2025


    //08-02-2025
    @GetMapping("/country")
    public ResponseEntity<List<String>> getCountry() {
        return ResponseEntity.ok(employeeBasicInformationService.getCountry()
                .stream().map(LocationLookup::getCountryName).distinct().toList());
    }

    @GetMapping("/division")
    public ResponseEntity<List<String>> getDivision(@RequestParam String countryName) {
        return ResponseEntity.ok(employeeBasicInformationService.getDivisionByCountryName(countryName)
                .stream().map(LocationLookup :: getDivisionName).distinct().toList());
    }

    @GetMapping("/district")
    public ResponseEntity<List<String>> getDistrict(@RequestParam String divisionName) {
        return ResponseEntity.ok(employeeBasicInformationService.getDistrictByDivision(divisionName)
                .stream().map(LocationLookup :: getDistrictName).distinct().toList());
    }

    @GetMapping("/thana")
    public ResponseEntity<List<String>> getThana(@RequestParam String districtName) {
        return ResponseEntity.ok(employeeBasicInformationService.getThanaByDistrict(districtName)
                .stream().map(LocationLookup :: getPoliceStationName).distinct().toList());
    }

    @GetMapping("/post-office")
    public ResponseEntity<List<String>> getPostOffice(@RequestParam String thanaName) {
        return ResponseEntity.ok(employeeBasicInformationService.getPostOfficeByThana(thanaName)
                .stream().map(LocationLookup :: getPostOfficeName).distinct().toList());
    }
    //08-02-2025


    //25 - 02 - 2025
    //Find employee by id
    @GetMapping("/find/{id}")
    public ResponseEntity<EmployeeBasicInformation> findEmployeeById(@PathVariable int id){
        EmployeeBasicInformation employee = employeeBasicInformationService.getEmployeeBasicInformationByEmployeeId(id);
        System.out.println(employee);
        return ResponseEntity.ok(employeeBasicInformationService.getEmployeeBasicInformationByEmployeeId(id));
    }
    //25 - 02 - 2025

    //20-03-2025

    //Contact Information Duplicate Check
    @GetMapping("/check-duplicate-reference/{value}")
    public ResponseEntity<Boolean> checkContactRef(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsContactRefID(value);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-duplicate-email/{value}")
    public ResponseEntity<Boolean> checkContactEmail(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsByEmail(value);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-duplicate-businessEmail/{value}")
    public ResponseEntity<Boolean> checkContactBusnEmail(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsByBusnEmail(value);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-duplicate-phNum/{value}")
    public ResponseEntity<Boolean> checkContactPhNum(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsByPhNum(value);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-duplicate-altNum/{value}")
    public ResponseEntity<Boolean> checkContactAltNum(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsByAltphNum(value);
        return ResponseEntity.ok(exists);
    }
    //Contact Information Duplicate Check


    //Education Information Duplicate Check
    @GetMapping("/check-duplicate-rollNo/{value}")
    public ResponseEntity<Boolean> checkEducationRollNo(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsByRollNo(value);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-duplicate-regNo/{value}")
    public ResponseEntity<Boolean> checkEducationRegNo(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsByRegNo(value);
        return ResponseEntity.ok(exists);
    }
    //Education Information Duplicate Check

    //Education Information Duplicate Check
    @GetMapping("/check-duplicate-familyRefID/{value}")
    public ResponseEntity<Boolean> checkFamilyRefID(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsByFamilyRefId(value);
        return ResponseEntity.ok(exists);
    }
    //Education Information Duplicate Check

    //Medical Information Duplicate Check
    @GetMapping("/check-duplicate-medicalRefID/{value}")
    public ResponseEntity<Boolean> checkMedicalRefID(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsByMedicalReferID(value);
        return ResponseEntity.ok(exists);
    }
    //Medical Information Duplicate Check

    //Police Information Duplicate Check
    @GetMapping("/check-duplicate-policeRefID/{value}")
    public ResponseEntity<Boolean> checkPoliceRefID(@PathVariable String value) {
        boolean exists = employeeBasicInformationService.existsByPoliceReferID(value);
        return ResponseEntity.ok(exists);
    }
    //Police Information Duplicate Check

    //20-03-2025

}


//Designation and emptype

