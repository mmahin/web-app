package web.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import web.core.models.entities.Institutions;
import web.rest.mvc.AccountController;
import web.rest.resources.InstitutionsResource;

public class InstitutionsResourceAsm extends
		ResourceAssemblerSupport<Institutions, InstitutionsResource> {

	public InstitutionsResourceAsm() {
		super(AccountController.class, InstitutionsResource.class);
	}

	public InstitutionsResource toResource(Institutions institution) {
		InstitutionsResource res = new InstitutionsResource();
		res.setName(institution.getName());
		res.setType(institution.getType());
		res.setDescription(institution.getDescription());
		res.setStart(institution.getStart());
		res.setEnd(institution.getEnd());

		res.add(linkTo(AccountController.class).slash(institution.getType())
				.slash(institution.getName()).withRel("institutions"));
		return res;
	}

}
