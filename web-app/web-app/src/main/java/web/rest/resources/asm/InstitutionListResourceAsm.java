package web.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import web.core.services.util.InstitutionList;
import web.rest.mvc.AccountController;
import web.rest.resources.InstitutionListResource;

public class InstitutionListResourceAsm extends
ResourceAssemblerSupport<InstitutionList, InstitutionListResource> {
public InstitutionListResourceAsm() {
super(AccountController.class, InstitutionListResource.class);
}

	public InstitutionListResource toResource(InstitutionList institutionList) {
		InstitutionListResource res = new InstitutionListResource();
		res.setInstitutions(new InstitutionsResourceAsm().toResources(institutionList.getInstitutions()));
		return res;
	}
}
