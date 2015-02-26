package web.core.services.util;

import java.util.ArrayList;
import java.util.List;

import web.core.models.entities.Institutions;

public class InstitutionList {

	private List<Institutions> institutions = new ArrayList<Institutions>();

	public InstitutionList(List<Institutions> list) {
		this.institutions = list;
	}

	public List<Institutions> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<Institutions> institutions) {
		this.institutions = institutions;
	}


}
