package web.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;


import web.core.services.util.RoomList;
import web.rest.mvc.RoomController;
import web.rest.resources.RoomListResource;

public class RoomListResourceAsm extends
ResourceAssemblerSupport<RoomList, RoomListResource> {
public RoomListResourceAsm() {
super(RoomController.class, RoomListResource.class);
}

	public RoomListResource toResource(RoomList roomList) {
		RoomListResource res = new RoomListResource();
		res.setRooms(new RoomResourceAsm().toResources(roomList.getRooms()));
		return res;
	}

}
