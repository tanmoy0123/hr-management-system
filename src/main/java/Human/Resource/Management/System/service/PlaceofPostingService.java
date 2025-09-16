package Human.Resource.Management.System.service;

import Human.Resource.Management.System.model.EmployeeBasicInformation;
import Human.Resource.Management.System.model.PlaceofPosting;

import java.util.List;

public interface PlaceofPostingService {
	List<PlaceofPosting> getAllPlaceofPostings();
	PlaceofPosting getById(Long id);
	void save(EmployeeBasicInformation employee);
	void update(Long id, PlaceofPosting placeofPosting);
	void delete(Long id);
}
