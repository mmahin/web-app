package web.core.services;

import web.core.models.entities.Account;
import web.core.models.entities.Room;
import web.core.services.util.AccountList;

public interface RoomService {

	public Room getRoom(String roomname);

	public String deleteRoom(String string);

	public AccountList addAdmin(String roomname, Account room);

	public AccountList removeAdmin(String roomname, Account room);

	public AccountList addMember(String roomname,Account account);

	public AccountList removeMember(String eq, Account any);

}
