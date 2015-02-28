package web.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class InstitutionListResource extends ResourceSupport {

	private List<InstitutionsResource> institutions = new ArrayList<InstitutionsResource>();

	public List<InstitutionsResource> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<InstitutionsResource> institutions) {
		this.institutions = institutions;
	}
}
