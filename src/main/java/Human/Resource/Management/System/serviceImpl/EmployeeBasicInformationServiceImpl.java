package Human.Resource.Management.System.serviceImpl;

import Human.Resource.Management.System.model.*;
import Human.Resource.Management.System.repository.*;
import Human.Resource.Management.System.service.EmployeeBasicInformationService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeBasicInformationServiceImpl implements EmployeeBasicInformationService {

	private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
	private final EmployeeBasicInformationRepository employeeRepo;
	private final EducationRepository educationRepo;
	private final PreviousExperienceRepository experienceRepo;
	private final FamilyInformationRepository familyRepo;
	private final MedicalInformationRepository medicalRepo;
	private final PoliceClearenceRepository policeClearanceRepo;
	private final AddressRepository addressRepo;
	private final ContactInformationRepository contactInformationRepo;
	private final CategoryRepository categoryRepository;
	private final EducationLookUpRepo educationLookUpRepo;
	private final UniversityLookupRepository universityLookupRepo;


	//06-02-2025
	private final GroupResultTypeLookupRepository groupResultTypeLookupRepo;

	//08-02-2025
	private final LocationLookupRepository locationRepo;

	//17-03-2025
	private final TestCenterLookupRepository testCenterLookupRepository;


	public EmployeeBasicInformationServiceImpl(EmployeeBasicInformationRepository employeeRepo, EducationRepository educationRepo, PreviousExperienceRepository experienceRepo, FamilyInformationRepository familyRepo, MedicalInformationRepository medicalRepo, PoliceClearenceRepository policeClearanceRepo, AddressRepository addressRepo, ContactInformationRepository contactInformationRepo, CategoryRepository categoryRepository, EducationLookUpRepo educationLookUpRepo, UniversityLookupRepository universityLookupRepo, GroupResultTypeLookupRepository groupResultTypeLookupRepo, LocationLookupRepository repository, LocationLookupRepository locationRepo, TestCenterLookupRepository testCenterLookupRepository) {
		this.employeeRepo = employeeRepo;
		this.educationRepo = educationRepo;
		this.experienceRepo = experienceRepo;
		this.familyRepo = familyRepo;
		this.medicalRepo = medicalRepo;
		this.policeClearanceRepo = policeClearanceRepo;
		this.addressRepo = addressRepo;
		this.contactInformationRepo = contactInformationRepo;
		this.categoryRepository = categoryRepository;
		this.educationLookUpRepo = educationLookUpRepo;
		this.universityLookupRepo = universityLookupRepo;

		//06-02-2025
		this.groupResultTypeLookupRepo = groupResultTypeLookupRepo;

		//08-02-2025
		this.locationRepo = locationRepo;
		this.testCenterLookupRepository = testCenterLookupRepository;
	}


	//     Save Employee Basic Information in the Database
//    @Transactional
	@Override
	public void saveEmployeeBasicInformation(EmployeeBasicInformation employee,
											 MultipartFile photo,
											 MultipartFile contactInformationRefDocs,
											 MultipartFile brcDocument,
											 MultipartFile marriageCertificateDocument,
											 MultipartFile nidDocumentOfRelationType,
											 List<MultipartFile> uploadEducationDocuments,
											 List<MultipartFile> uploadExperienceDocuments,
											 List<MultipartFile> uploadMedicalDocuments,
											 List<MultipartFile> uploadPoliceClearanceDocuments,
											 boolean marked,
											 boolean marked2,
											 boolean isAlive) {

		try {

//			EmployeeBasicInformation test = employeeRepo.testQuery(1);
//			System.out.println(test.toString());

			// Generate the employee_id
			Integer permanentEmployeeID = employeeRepo.permanentEmployeeId(); // Fetch the highest permanentEmployeeID
			Integer temporaryEmployeeID = employeeRepo.temporaryEmployeeId(); // Fetch the highest temporaryEmployeeID
			Integer contractEmployeeID = employeeRepo.contractEmployeeId(); // Fetch the highest contractEmployeeID

//			Integer newEmployeeId = (latestEmployeeId == null ? 200001 : latestEmployeeId); // Start from 200001 or increment
//			System.out.println(newEmployeeId);
//			employee.setEmployeeId(newEmployeeId); // Assign the generated employee_id
			String jobType = employee.getJobType();
//			System.out.println(jobType);

			if (jobType.equals("permanent")){
				employee.setEmployeeId(permanentEmployeeID == null ? 200001 : permanentEmployeeID+1);
			}

			if (jobType.equals("temporary")){
				employee.setEmployeeId(temporaryEmployeeID == null ? 200001 : temporaryEmployeeID+1);
			}

			if (jobType.equals("contractual")){
				employee.setEmployeeId(contractEmployeeID == null ? 200001 : contractEmployeeID+1);
			}

			// Set activeStatus to true and createdOrUpdatedOn to current time
			employee.setActiveStatus(1);

			//Created
			employee.setCreatedOrUpdatedOn(LocalDateTime.now());
			employee.setCreatedOrUpdatedBy("Developer");

			//Deactivated
			employee.setDeactivatedBy("Developer");
			employee.setDeactivatedOn(LocalDateTime.now());

			// Save employee record
			EmployeeBasicInformation savedEmployee = employeeRepo.save(employee);
			System.out.println("Employee Saved successfully.");
			// Save photo and NID document to static folder
			// Save Photo to Static Folder
			if (photo != null && !photo.isEmpty()) {
				String photoPath = saveFileToStaticFolder(photo, savedEmployee.getId() + "_photo");
				savedEmployee.setPhotoUrl(photoPath);
			}

			// Save the employee after setting photo URL and NID document
			employeeRepo.save(savedEmployee);

			// Save Contact Informations Details
			if (employee.getContactInformationList() != null) {
				ContactInformation contactInformation = employee.getContactInformationList().get(0);

				if (contactInformationRefDocs != null && !contactInformationRefDocs.isEmpty()) {
					contactInformation.setRefDocs(contactInformationRefDocs.getBytes());
				}

				contactInformation.setEmployeeBasicInformation(savedEmployee);
				contactInformationRepo.save(contactInformation);

			}


			// Save Education Details
			if (employee.getEducationList() != null) {
				for (int i = 0; i < employee.getEducationList().size(); i++) {
					Education education = employee.getEducationList().get(i);
					education.setEmployeeBasicInformation(savedEmployee);

					// If documents are provided for education
					if (uploadEducationDocuments != null && i < uploadEducationDocuments.size() && !uploadEducationDocuments.get(i).isEmpty()) {
						String docPath = saveFileToStaticFolder(uploadEducationDocuments.get(i), savedEmployee.getId() + "_education_" + i);
						education.setDocumentPath(docPath);
					}


					educationRepo.save(education); // Save each education record
				}
			}


			// Process Previous Experience
			if (employee.getPreviousExperienceList() != null) {
				for (int i = 0; i < employee.getPreviousExperienceList().size(); i++) {
					PreviousExperience experience = employee.getPreviousExperienceList().get(i);
					experience.setEmployeeBasicInformation(savedEmployee);

					// Handle file upload for experience documents
					if (uploadExperienceDocuments != null && i < uploadExperienceDocuments.size() && !uploadExperienceDocuments.get(i).isEmpty()) {
						String docPath = saveFileToStaticFolder(uploadExperienceDocuments.get(i), savedEmployee.getId() + "_experience_" + i);
						experience.setDocumentPath(docPath);
					}

					experienceRepo.save(experience);
				}
			}


			// Save Family Details
			if (employee.getFamilyList() != null) {
				Family family = employee.getFamilyList().get(0);

				int age = findAge(family.getDateOfBirth());

				if (brcDocument != null && !brcDocument.isEmpty()) {
					String photoPath = saveFileToStaticFolder(brcDocument, savedEmployee.getEmployeeId() + "_brcDocumentOf" + family.getRelationType());
					family.setBrcPath(photoPath);
				}

				if (marriageCertificateDocument != null && !marriageCertificateDocument.isEmpty() && (family.getRelationType().equals("spouse"))) {
					String photoPath = saveFileToStaticFolder(marriageCertificateDocument, savedEmployee.getEmployeeId() + "_marriageCertificate");
					family.setMarrigeCertificatePath(photoPath);
				}


				if (nidDocumentOfRelationType != null && !nidDocumentOfRelationType.isEmpty()) {
					String photoPath = saveFileToStaticFolder(nidDocumentOfRelationType, savedEmployee.getEmployeeId() + "_nidDocumentOf" + family.getRelationType());
					family.setNidPath(photoPath);
				}


				if (isAlive) {
					family.setAlive(0);
				} else {
					family.setAlive(1);
				}

				Category category = categoryRepository.findByRelationType(family.getRelationType()).orElseThrow(() -> new RuntimeException("Invalid relationType: " + family.getRelationType()));

				family.setRelationCode(category.getCode());
				family.setAge(age);

				family.setEmployeeBasicInformation(savedEmployee);
				familyRepo.save(family);

			}

			// Save Medical Details
			if (employee.getMedicalList() != null) {
				for (int i = 0; i < employee.getMedicalList().size(); i++) {
					Medical medical = employee.getMedicalList().get(i);
					medical.setEmployeeBasicInformation(savedEmployee);


					if (uploadMedicalDocuments != null && i < uploadMedicalDocuments.size() && !uploadMedicalDocuments.get(i).isEmpty()) {
						String docPath = saveFileToStaticFolder(uploadMedicalDocuments.get(i), savedEmployee.getId() + "_medical_" + i);
						medical.setDocumentPath(docPath);
					}

					medicalRepo.save(medical);
					System.out.println(medical.getEmployeeBasicInformation().getEmployeeId());
				}
			}


			//Save Police Clearance
			if (employee.getPoliceClearanceList() != null) {
				for (int i = 0; i < employee.getPoliceClearanceList().size(); i++) {
					PoliceClearance policeClearance = employee.getPoliceClearanceList().get(i);
					policeClearance.setEmployeeBasicInformation(savedEmployee);


					if (uploadPoliceClearanceDocuments != null && i < uploadPoliceClearanceDocuments.size() && !uploadPoliceClearanceDocuments.get(i).isEmpty()) {
						String docPath = saveFileToStaticFolder(uploadPoliceClearanceDocuments.get(i), savedEmployee.getId() + "_policeClearance_" + i);
						policeClearance.setDocumentPath(docPath);
					}

					policeClearanceRepo.save(policeClearance);
				}
			}

			//Save Address
			if (employee.getAddressList() != null) {
				Address address = employee.getAddressList().get(0);

				if (marked) {
					address.setPrsAddr(address.getPrmAddr());
					address.setPrsDist(address.getPrmDist());
					address.setPrsCountry(address.getPrmCountry());
					address.setPrsPO(address.getPrmPO());
					address.setPrsPS(address.getPrmPS());
					address.setPrsDiv(address.getPrmDiv());
				}

				if (marked2) {
					address.setConAddr(address.getPrsAddr());
					address.setConDist(address.getPrsDist());
					address.setConCountry(address.getPrsCountry());
					address.setConPO(address.getPrsPO());
					address.setConPS(address.getPrsPS());
					address.setConDiv(address.getPrsDiv());
				}

				address.setEmployeeBasicInformation(savedEmployee);
				addressRepo.save(address);
			}


		} catch (IOException e) {
			throw new RuntimeException("Failed to save employee data", e);
		}

	}


	@Override
	public void updateEmployeeBasicInformation(long id, EmployeeBasicInformation updatedEmployee, MultipartFile photo, MultipartFile contactInformationRefDocs, MultipartFile brcDocument,
											   MultipartFile marriageCertificateDocument, MultipartFile nidDocumentOfRelationType, List<MultipartFile> uploadEducationDocuments, List<MultipartFile> uploadExperienceDocuments, List<MultipartFile> uploadMedicalDocuments, List<MultipartFile> uploadPoliceClearanceDocuments, boolean marked, boolean marked2, boolean isAlive) {
		try {


			EmployeeBasicInformation findEmployee = employeeRepo.findById((int) id).get();

			//Set updated employee
			findEmployee.setFirstName(updatedEmployee.getFirstName());
			findEmployee.setLastName(updatedEmployee.getLastName());
			findEmployee.setFamilyName(updatedEmployee.getFamilyName());
			findEmployee.setFathersName(updatedEmployee.getFathersName());
			findEmployee.setMothersName(updatedEmployee.getMothersName());

			if (updatedEmployee.getDob() != null) {
				findEmployee.setDob(updatedEmployee.getDob());
			} else {
				findEmployee.setDob(findEmployee.getDob());
			}

			findEmployee.setReligion(updatedEmployee.getReligion());
			findEmployee.setBloodGroup(updatedEmployee.getBloodGroup());
			findEmployee.setNationality(updatedEmployee.getNationality());
			findEmployee.setMaritalStatus(updatedEmployee.getMaritalStatus());


			// Save Photo to Static Folder
			if (photo != null && !photo.isEmpty()) {
				String photoPath = saveFileToStaticFolder(photo, findEmployee.getId() + "_photo");
				findEmployee.setPhotoUrl(photoPath);
			} else {
				findEmployee.setPhotoUrl(findEmployee.getPhotoUrl());
			}

			// Save the employee after setting photo URL and NID document
			employeeRepo.save(findEmployee);

			//Save Update Contact Information
			if (updatedEmployee.getContactInformationList() != null) {
				ContactInformation contactInformation = updatedEmployee.getContactInformationList().get(0);
				ContactInformation updatedContactInformation = findEmployee.getContactInformationList().get(0);


				if (contactInformationRefDocs != null && !contactInformationRefDocs.isEmpty()) {
					contactInformation.setRefDocs(contactInformationRefDocs.getBytes());
				} else {
					updatedContactInformation.setRefDocs(updatedContactInformation.getRefDocs());
				}

				updatedContactInformation.setIdType(contactInformation.getIdType());
				updatedContactInformation.setRefId(contactInformation.getRefId());
				updatedContactInformation.setIssDate(contactInformation.getIssDate());
				updatedContactInformation.setIssCountry(contactInformation.getIssCountry());
				updatedContactInformation.setIssPlace(contactInformation.getIssPlace());
				updatedContactInformation.setExpDate(contactInformation.getExpDate());
				updatedContactInformation.setEmail(contactInformation.getEmail());
				updatedContactInformation.setBusnEmail(contactInformation.getBusnEmail());
				updatedContactInformation.setPhNum(contactInformation.getPhNum());
				updatedContactInformation.setAltphNum(contactInformation.getAltphNum());
				updatedContactInformation.setSmediaLink(contactInformation.getSmediaLink());
				updatedContactInformation.setEmployeeBasicInformation(findEmployee);

				contactInformationRepo.save(updatedContactInformation);
			}

//             Save Education Details
			if (updatedEmployee.getEducationList() != null) {
				int sizeOfEmployeeEducation = findEmployee.getEducationList().size();
				int sizeOfUpdatedEmployeeEducation = updatedEmployee.getEducationList().size();


				for (int i = 0; i < uploadEducationDocuments.size(); i++) {

					Education updatedEducation = updatedEmployee.getEducationList().get(i);


					if (i >= sizeOfEmployeeEducation) {

						if (uploadEducationDocuments != null && !uploadEducationDocuments.get(i).isEmpty()) {
							String docPath = saveFileToStaticFolder(uploadEducationDocuments.get(i), findEmployee.getId() + "_education_" + i);
							updatedEducation.setDocumentPath(docPath);
						} else {
							updatedEducation.setDocumentPath(null);
						}

						updatedEducation.setEmployeeBasicInformation(findEmployee);
						educationRepo.save(updatedEducation);
					} else {
						Education education = findEmployee.getEducationList().get(i);


						if (updatedEducation.getPassingYear() != null) {
							education.setPassingYear(updatedEducation.getPassingYear());
						} else {
							education.setPassingYear(education.getPassingYear());
						}

						education.setLevelOfEducation(updatedEducation.getLevelOfEducation());
						education.setExamDegreeTitle(updatedEducation.getExamDegreeTitle());
						education.setBoardUniversity(updatedEducation.getBoardUniversity());
						education.setResultType(updatedEducation.getResultType());
						education.setMarks(updatedEducation.getMarks());
						education.setCgpa(updatedEducation.getCgpa());
						education.setGpa(updatedEducation.getGpa());
						education.setGroupName(updatedEducation.getGroupName());
						education.setSchoolCollege(updatedEducation.getSchoolCollege());


						System.out.println("Uploaded Education document size: " + uploadEducationDocuments.size());
						if (uploadEducationDocuments != null && i < uploadEducationDocuments.size() && !uploadEducationDocuments.get(i).isEmpty()) {
							String docPath = saveFileToStaticFolder(uploadEducationDocuments.get(i), findEmployee.getId() + "_education_" + i);
							education.setDocumentPath(docPath);
						} else {
							education.setDocumentPath(education.getDocumentPath());
						}

						education.setEmployeeBasicInformation(findEmployee);
						educationRepo.save(education);
					}


				}
			}


			// Process Previous Experience
			if (updatedEmployee.getPreviousExperienceList() != null) {
				int sizeOfEmployeeExperience = findEmployee.getPreviousExperienceList().size();
				int sizeOfUpdatedExperience = updatedEmployee.getPreviousExperienceList().size();

				for (int i = 0; i < sizeOfUpdatedExperience; i++) {

					PreviousExperience updatedExperience = updatedEmployee.getPreviousExperienceList().get(i);

					if (i >= sizeOfEmployeeExperience) {
						// Handle file upload for experience documents
						if (uploadExperienceDocuments != null && i < uploadExperienceDocuments.size() && !uploadExperienceDocuments.get(i).isEmpty()) {
							String docPath = saveFileToStaticFolder(uploadExperienceDocuments.get(i), findEmployee.getId() + "_experience_" + i);
							updatedExperience.setDocumentPath(docPath);
						} else {
							updatedExperience.setDocumentPath(null);
						}

						updatedExperience.setEmployeeBasicInformation(findEmployee);
						experienceRepo.save(updatedExperience);
					} else {
						PreviousExperience experience = findEmployee.getPreviousExperienceList().get(i);

						experience.setCompanyBusiness(updatedExperience.getCompanyBusiness());
						experience.setCompanyBusiness2(updatedExperience.getCompanyBusiness2());
						experience.setCompanyName(updatedExperience.getCompanyName());
						experience.setAreaDivision(updatedExperience.getAreaDivision());
						experience.setDeptFunction(updatedExperience.getDeptFunction());
						experience.setBranchLocation(updatedExperience.getBranchLocation());
						experience.setAreaExpertise(updatedExperience.getAreaExpertise());
						experience.setDesignation(updatedExperience.getDesignation());
						experience.setJobResponsibilities(updatedExperience.getJobResponsibilities());

						if (updatedExperience.getStartDate() != null) {
							experience.setStartDate(updatedExperience.getStartDate());
						} else {
							experience.setStartDate(experience.getStartDate());
						}

						if (updatedExperience.getEndDate() != null) {
							experience.setEndDate(updatedExperience.getEndDate());
						} else {
							experience.setEndDate(experience.getEndDate());
						}


						// Handle file upload for experience documents
						if (uploadExperienceDocuments != null && i < uploadExperienceDocuments.size() && !uploadExperienceDocuments.get(i).isEmpty()) {
							String docPath = saveFileToStaticFolder(uploadExperienceDocuments.get(i), findEmployee.getId() + "_experience_" + i);
							experience.setDocumentPath(docPath);
						} else {
							experience.setDocumentPath(experience.getDocumentPath());
						}

						experience.setEmployeeBasicInformation(findEmployee);
						experienceRepo.save(experience);
					}

				}
			}


			// Save Family Details
			if (updatedEmployee.getFamilyList() != null) {
				Family updatedFamily = updatedEmployee.getFamilyList().get(0);
				Family family = findEmployee.getFamilyList().get(0);


				if (updatedFamily.getDateOfBirth() != null) {
					int age = findAge(updatedFamily.getDateOfBirth());
					family.setAge(age);
				} else {
					family.setAge(family.getAge());
				}


				if (brcDocument != null && !brcDocument.isEmpty()) {
					String photoPath = saveFileToStaticFolder(brcDocument, findEmployee.getEmployeeId() + "_brcDocumentOf" + updatedFamily.getRelationType());
					family.setBrcPath(photoPath);
				} else {
					family.setBrcPath(family.getBrcPath());
				}


				if (marriageCertificateDocument != null && !marriageCertificateDocument.isEmpty() && updatedFamily.getRelationType().equals("spouse")) {
					String photoPath = saveFileToStaticFolder(marriageCertificateDocument, findEmployee.getEmployeeId() + "_marriageCertificateOfSpouse");
					family.setMarrigeCertificatePath(photoPath);
				} else {
					family.setMarrigeCertificatePath(family.getMarrigeCertificatePath());
				}


				if (nidDocumentOfRelationType != null && !nidDocumentOfRelationType.isEmpty()) {
					String photoPath = saveFileToStaticFolder(nidDocumentOfRelationType, findEmployee.getEmployeeId() + "_marriageCertificateOfSpouse");
					family.setNidPath(photoPath);
				} else {
					family.setNidPath(family.getNidPath());
				}

				if (isAlive) {
					family.setAlive(0);
				} else {
					family.setAlive(1);
				}

				Category category = categoryRepository.findByRelationType(updatedFamily.getRelationType()).orElseThrow(() -> new RuntimeException("Invalid relationType: " + updatedFamily.getRelationType()));

				family.setRelationCode(category.getCode());


				family.setRelationType(updatedFamily.getRelationType());
				family.setName(updatedFamily.getName());
				family.setGender(updatedFamily.getGender());
				family.setDateOfBirth(updatedFamily.getDateOfBirth());

				family.setEmployeeBasicInformation(findEmployee);
				familyRepo.save(family);


			}

			// Save Medical Details
			if (updatedEmployee.getMedicalList() != null) {
				int sizeOfEmployee = findEmployee.getMedicalList().size();
				int sizeOfUpdatedMedical = updatedEmployee.getMedicalList().size();

				for (int i = 0; i < sizeOfUpdatedMedical; i++) {
					Medical updatedMedical = updatedEmployee.getMedicalList().get(i);

					if (i >= sizeOfEmployee) {
						if (uploadMedicalDocuments != null && i < uploadMedicalDocuments.size() && !uploadMedicalDocuments.get(i).isEmpty()) {
							String docPath = saveFileToStaticFolder(uploadMedicalDocuments.get(i), findEmployee.getId() + "_medical_" + i);
							updatedMedical.setDocumentPath(docPath);
						} else {
							updatedMedical.setDocumentPath(null);
						}

						updatedMedical.setEmployeeBasicInformation(findEmployee);
						medicalRepo.save(updatedMedical);

					} else {
						Medical medical = findEmployee.getMedicalList().get(i);
						medical.setDiagnosisLevel(updatedMedical.getDiagnosisLevel());
						medical.setTestCenter(updatedMedical.getTestCenter());

						if (uploadMedicalDocuments != null && i < uploadMedicalDocuments.size() && !uploadMedicalDocuments.get(i).isEmpty()) {
							String docPath = saveFileToStaticFolder(uploadMedicalDocuments.get(i), findEmployee.getId() + "_medical_" + i);
							medical.setDocumentPath(docPath);
						} else {
							medical.setDocumentPath(medical.getDocumentPath());
						}

						medical.setEmployeeBasicInformation(findEmployee);
						medicalRepo.save(medical);
					}


				}
			}


			// Save police clearance Details
			if (updatedEmployee.getPoliceClearanceList() != null) {
				int sizeOfEmployee = findEmployee.getPoliceClearanceList().size();
				int sizeOfUpdatedPoliceClearance = updatedEmployee.getPoliceClearanceList().size();

				for (int i = 0; i < sizeOfUpdatedPoliceClearance; i++) {

					PoliceClearance updatedPoliceClearance = updatedEmployee.getPoliceClearanceList().get(i);

					if (i >= sizeOfEmployee) {
						if (uploadPoliceClearanceDocuments != null && i < uploadPoliceClearanceDocuments.size() && !uploadPoliceClearanceDocuments.get(i).isEmpty()) {
							String docPath = saveFileToStaticFolder(uploadPoliceClearanceDocuments.get(i), findEmployee.getId() + "_policeClearance_" + i);
							updatedPoliceClearance.setDocumentPath(docPath);
						} else {
							updatedPoliceClearance.setDocumentPath(null);
						}

						updatedPoliceClearance.setEmployeeBasicInformation(findEmployee);
						policeClearanceRepo.save(updatedPoliceClearance);
					} else {
						PoliceClearance policeClearance = findEmployee.getPoliceClearanceList().get(i);
						policeClearance.setThana(updatedPoliceClearance.getThana());
						policeClearance.setClearanceDate(updatedPoliceClearance.getClearanceDate());

						if (uploadPoliceClearanceDocuments != null && i < uploadPoliceClearanceDocuments.size() && !uploadPoliceClearanceDocuments.get(i).isEmpty()) {
							String docPath = saveFileToStaticFolder(uploadPoliceClearanceDocuments.get(i), findEmployee.getId() + "_policeClearance_" + i);
							policeClearance.setDocumentPath(docPath);
						} else {
							policeClearance.setDocumentPath(policeClearance.getDocumentPath());
						}

						policeClearance.setEmployeeBasicInformation(findEmployee);
						policeClearanceRepo.save(policeClearance);
					}

				}
			}

			//Update address fields
			if (findEmployee.getAddressList() != null) {
				Address address = findEmployee.getAddressList().get(0);
				Address updatedAddress = updatedEmployee.getAddressList().get(0);

				if (marked) {
					updatedAddress.setPrsAddr(updatedAddress.getPrmAddr());
					updatedAddress.setPrsDist(updatedAddress.getPrmDist());
					updatedAddress.setPrsCountry(updatedAddress.getPrmCountry());
					updatedAddress.setPrsPS(updatedAddress.getPrmPS());
					updatedAddress.setPrsPO(updatedAddress.getPrmPO());
					updatedAddress.setPrsDiv(updatedAddress.getPrmDiv());
				}

				if (marked2) {
					updatedAddress.setConAddr(updatedAddress.getPrsAddr());
					updatedAddress.setConDist(updatedAddress.getPrsDist());
					updatedAddress.setConCountry(updatedAddress.getPrsCountry());
					updatedAddress.setConPS(updatedAddress.getPrmPS());
					updatedAddress.setConPO(updatedAddress.getPrmPO());
					updatedAddress.setConDiv(updatedAddress.getPrsDiv());
				}
				address.setPrsAddr(updatedAddress.getPrsAddr());
				address.setPrmAddr(updatedAddress.getPrmAddr());
				address.setConAddr(updatedAddress.getConAddr());

				address.setPrsCountry(updatedAddress.getPrsCountry());
				address.setPrmCountry(updatedAddress.getPrmCountry());
				address.setConCountry(updatedAddress.getConCountry());

				address.setPrsDist(updatedAddress.getPrsDist());
				address.setPrmDist(updatedAddress.getPrmDist());
				address.setConDist(updatedAddress.getConDist());

				address.setPrsPS(updatedAddress.getPrsPS());
				address.setPrmPS(updatedAddress.getPrmPS());
				address.setConPS(updatedAddress.getConPS());

				address.setPrsPO(updatedAddress.getPrsPO());
				address.setPrmPO(updatedAddress.getPrmPO());
				address.setConPO(updatedAddress.getConPO());

				address.setPrsDiv(updatedAddress.getPrsDiv());
				address.setPrmDiv(updatedAddress.getPrmDiv());
				address.setConDiv(updatedAddress.getConDiv());

				addressRepo.save(address);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to save employee data", e);
		}
	}

	@Override
	public List<EmployeeBasicInformation> allEmployee() {
		return employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}


	//    Deactivate Employee Basic Information from Database
	@Override
	public void deleteEmployeeById(Integer id) {
		if (employeeRepo.existsById(id)) {
			employeeRepo.deleteById(id); // Delete the employee by ID
		} else {
			throw new RuntimeException("Employee not found with ID: " + id);
		}

	}


	@Override
	public void deactivateEmployeeBasicInformation(Integer id) {
		Optional<EmployeeBasicInformation> optionalEmployeeBasicInformation = employeeRepo.findById(id);
		if (optionalEmployeeBasicInformation.isPresent()) {
			EmployeeBasicInformation employeeBasic = optionalEmployeeBasicInformation.get();
			employeeBasic.setActiveStatus(0);

			employeeRepo.save(employeeBasic);

		}


	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	//Get Education All Education Levels
	@Override
	public List<EducationLookUp> getAllEducationLevels() {
		return educationLookUpRepo.findAll();
	}

	//Get All Education Levels by Exam Title
	@Override
	public List<EducationLookUp> getExamTitlesByLevel(String levelOfEducation) {
//        return educationLookUpRepo.findByLevelOfEducation(levelOfEducation);

		return educationLookUpRepo.findByLevelOfEducation(levelOfEducation);
	}

	@Override
	public List<UniversityLookup> getAllUniversities() {
		return universityLookupRepo.findAll();
	}

	@Override
	public List<Object[]> getSchoolsByBoard(String boardName) {
		return universityLookupRepo.findSchoolsByBoard(boardName);
	}

	@Override
	public List<Object[]> getCollegesByBoard(String boardName) {
		return universityLookupRepo.findCollegesByBoard(boardName);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}


	@Override
	public EmployeeBasicInformation getEmployeeBasicInformation(int id) {
		// Fetch employee information from the repository
		return employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
	}

	@Override
	public EmployeeBasicInformation getEmployeeBasicInformationByEmployeeId(int id) {
		try {
			return employeeRepo.findByEmployeeId(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Employee not found with ID: " + id);
		}
	}

	private String saveFileToStaticFolder(MultipartFile file, String fileName) throws IOException {
		String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		String filePath = UPLOAD_DIR + fileName + "." + fileExtension;

		Path uploadPath = Paths.get(UPLOAD_DIR);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		}

		return "/uploads/" + fileName + "." + fileExtension;
	}


	//Find age by birthdate
	private int findAge(String dateOfBirth) {
		LocalDate date = LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_DATE);
		LocalDate date2 = LocalDate.now();
		Period period = Period.between(date, date2);
		return period.getYears();
	}


	//06-02-2025
	@Override
	public List<GroupResultTypeLookup> getGroupResultType() {
		return groupResultTypeLookupRepo.findAll();
	}
	//06-02-2025


	//08-02-2025
	@Override
	public List<LocationLookup> getCountry() {
		return locationRepo.findAll();
	}

	@Override
	public List<LocationLookup> getDivisionByCountryName(String countryName) {
		return locationRepo.findByCountryName(countryName);
	}

	@Override
	public List<LocationLookup> getDistrictByDivision(String divisionName) {
		return locationRepo.findByDivisionName(divisionName);
	}

	@Override
	public List<LocationLookup> getThanaByDistrict(String districtName) {
		return locationRepo.findByDistrictName(districtName);
	}

	@Override
	public List<LocationLookup> getPostOfficeByThana(String thanaName) {
		return locationRepo.findByPoliceStationName(thanaName);
	}
	//08-02-2025


	//20-03-2025
	@Override
	public List<TestCenterLookup> findAllTestCenter() {
		return testCenterLookupRepository.findAll();
	}

	@Override
	public boolean existsContactRefID(String firstName) {
		return contactInformationRepo.existsByRefId(firstName);
	}

	@Override
	public boolean existsByEmail(String email) {
		return contactInformationRepo.existsByEmail(email);
	}

	@Override
	public boolean existsByBusnEmail(String email) {
		return contactInformationRepo.existsByBusnEmail(email);
	}

	@Override
	public boolean existsByPhNum(String phNum) {
		return contactInformationRepo.existsByPhNum(phNum);
	}

	@Override
	public boolean existsByAltphNum(String altNum) {
		return contactInformationRepo.existsByAltphNum(altNum);
	}

	@Override
	public boolean existsByRollNo(String rollNo) {
		return educationRepo.existsByRollNo(rollNo);
	}

	@Override
	public boolean existsByRegNo(String regNo) {
		return  educationRepo.existsByRegNo(regNo);
	}

	@Override
	public boolean existsByFamilyRefId(String refID) {
		return familyRepo.existsByRefId(refID);
	}

	@Override
	public boolean existsByMedicalReferID(String refID) {
		return medicalRepo.existsByReferID(refID);
	}

	@Override
	public boolean existsByPoliceReferID(String refID) {
		return policeClearanceRepo.existsByReferID(refID);
	}


	//20-03-2025
}