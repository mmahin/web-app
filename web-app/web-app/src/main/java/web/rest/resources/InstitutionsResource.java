package web.rest.resources;

import java.sql.Date;

import org.springframework.hateoas.ResourceSupport;

import web.core.models.entities.Institutions;

public class InstitutionsResource extends ResourceSupport {

	private String name;
	private String type;
	private Date start;
	private Date end;
	private String description;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Institutions toInstitutions(){
		Institutions institution=new Institutions();
		institution.setName(name);
		institution.setType(type);
		institution.setDescription(description);
		institution.setStart(start);
		institution.setEnd(end);
		return institution;
		
	}

}
