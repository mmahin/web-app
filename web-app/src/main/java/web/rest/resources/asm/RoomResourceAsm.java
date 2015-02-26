package web.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import web.core.models.entities.Room;
import web.rest.mvc.RoomController;
import web.rest.resources.RoomResource;

public class RoomResourceAsm extends ResourceAssemblerSupport<Room,RoomResource> {

	public RoomResourceAsm() {
		super(RoomController.class,RoomResource.class);
	}

	public RoomResource toResource(Room room) {
		RoomResource resource = new RoomResource();
		resource.setRoomname(room.getRoomname());
		resource.add(linkTo(RoomController.class).slash(room.getRoomname()).withSelfRel());
		return resource;
	}

}
