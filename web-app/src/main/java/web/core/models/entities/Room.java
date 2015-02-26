package web.core.models.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Room {

	  @Id
	  @GeneratedValue
	  private long roomid;
		@Column(name="roomname",unique=true,nullable=false)
	  private String roomname;
	  private String description;
	  @ManyToMany
		@JoinTable(name = "Room_admins", joinColumns = @JoinColumn(name="Room_id"), inverseJoinColumns = @JoinColumn(name="Account_id"))
	  private Collection<Account> admins=new ArrayList<Account>();
	  @OneToMany
		@JoinTable(name = "Room_members", joinColumns = @JoinColumn(name="Room_id"), inverseJoinColumns = @JoinColumn(name="Account_id"))
		private Collection<Account> members = new ArrayList<Account>();

	public long getRoomid() {
		return roomid;
	}
	public void setRoomid(long roomid) {
		this.roomid = roomid;
	}
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

}
