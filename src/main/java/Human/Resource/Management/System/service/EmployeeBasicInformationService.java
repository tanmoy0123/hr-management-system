package Human.Resource.Management.System.service;

import Human.Resource.Management.System.model.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeBasicInformationService {

    void saveEmployeeBasicInformation(
            EmployeeBasicInformation employee,
            MultipartFile imageDocument,
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
            boolean isAlive
    );



    void updateEmployeeBasicInformation(
            long id,
            EmployeeBasicInformation updatedEmployee,
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
            boolean isAlive
    );

    List<EmployeeBasicInformation> allEmployee();



    void deleteEmployeeById(Integer id);

    EmployeeBasicInformation getEmployeeBasicInformation(int id);
    EmployeeBasicInformation getEmployeeBasicInformationByEmployeeId(int id);


    void deactivateEmployeeBasicInformation(Integer id);



    public List<Category> getAllCategories();

    public List<EducationLookUp>  getAllEducationLevels();
    public List<EducationLookUp> getExamTitlesByLevel(String levelOfEducation);

    public List<UniversityLookup> getAllUniversities();


    public List<Object[]> getSchoolsByBoard(String boardName);
    public List<Object[]> getCollegesByBoard(String boardName);

    public List<Category> findAll();


    //06-02-2025
    public List<GroupResultTypeLookup> getGroupResultType();
    //06-02-2025

    //08-02-2025
    public List<LocationLookup> getCountry() ;

    public List<LocationLookup> getDivisionByCountryName(String countryName);

    public List<LocationLookup> getDistrictByDivision(String divisionName) ;

    public List<LocationLookup> getThanaByDistrict(String districtName);

    public List<LocationLookup> getPostOfficeByThana(String thanaName) ;
    //08-02-2025

    //09-03-2025



    //17-03-2025
    public List<TestCenterLookup> findAllTestCenter();
    public boolean existsContactRefID(String firstName);
    public boolean existsByEmail(String email);
    public boolean existsByBusnEmail(String email);
    public boolean existsByPhNum(String phNum);
    public boolean existsByAltphNum(String altNum);
    public boolean existsByRollNo(String rollNo);
    public boolean existsByRegNo(String regNo);
    public boolean existsByFamilyRefId(String refID);
    public boolean existsByMedicalReferID(String refId);
    public boolean existsByPoliceReferID(String refId);
    //17-03-2025
}
