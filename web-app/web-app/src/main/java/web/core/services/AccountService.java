package web.core.services;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import web.core.models.entities.Account;
import web.core.models.entities.Institutions;
import web.core.models.entities.Room;
import web.core.services.util.AccountList;
import web.core.services.util.InstitutionList;
import web.core.services.util.RoomList;

public interface AccountService {

	public Account createAccount(Account account);

	public Account findAccount(String username);

	public Account verifyAccount(Account account);

	public Room createRoom(String username, Room room);

	public RoomList findRoom(String name);

	public AccountList findAccounts(String check);

	public RoomList findBookmarkedRooms(String name);

	public RoomList findOwnedRooms(String name);

	public AccountList getBookmarkedAccounts(String check);

	public Account updateAccountInfo(Account account);

	public Account updateAccountImage(MultipartFile file, String username) throws Exception;

	public File getAccountImage(String username) throws Exception;

	public Institutions addInstitution(Institutions institution, String username);

	public String deleteInstitution(Institutions institution,
			String username);

	public Account changeAccountPassword(Account account_old, Account account_new);


	public AccountList bookmarkAccount(String username_own, String username_follow);
	
	public InstitutionList getInstitutions(String username);

	public InstitutionList searchInstitutions(String check);
}
