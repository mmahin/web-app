package web.rest.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.hateoas.ResourceSupport;

import web.core.models.entities.Account;
import web.core.models.entities.Room;

public class RoomResource extends ResourceSupport {

	Room room=new Room();
	  private String roomname;
	  private String description;
	  private Collection<Account> admins=new ArrayList<Account>();
		private Collection<Account> members = new ArrayList<Account>();
	  
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Collection<Account> getAdmins() {
		return admins;
	}
	public void setAdmins(Collection<Account> admins) {
		this.admins = admins;
	}
	public Collection<Account> getMembers() {
		return members;
	}
	public void setMembers(Collection<Account> members) {
		this.members = members;
	}
	public Room toRoom() {
		room.setRoomname(roomname);
		room.setDescription(description);
		room.setAdmins(admins);
		room.setMembers(members);
		return room;
	}

}
