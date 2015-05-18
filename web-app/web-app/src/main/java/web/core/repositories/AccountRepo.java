package web.core.repositories;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

import web.core.models.entities.Account;
import web.core.models.entities.Institutions;
import web.core.models.entities.Room;
import web.core.services.util.AccountList;
import web.core.services.util.InstitutionList;
import web.core.services.util.RoomList;

public interface AccountRepo {

	//Service createAccount handle this
	public Account createAccount(Account account);

	//Service findAccount handle this
	public Account findAccount(String username);

	//Service verifyAccount handle this two
	public Account verifyAccountWithUsernamePassword(Account account);

	public Account verifyAccountWithEmailPassword(Account account);

	public Account changeAccountPassword(Account account_old, Account account_new);

	public Account bookmarkAccount(Account user_own, Account user_follow);

	public Account setBookmarkedRoom(Account account, Room room);

	public Account updateAccountFirstName(Account updatedaccount);

	public Account updateAccountLastName(Account updatedaccount);

	public Account updateAccountCity(Account updatedaccount);

	public Account updateAccountCountry(Account updatedaccount);

	public Account updateAccountAbount(Account updatedaccount);

//This part related to Account List
	public AccountList findAccounts(String check);

	public AccountList getBookmarkedAccounts(String check);
	
//This Part related to Account Images
	public File getAccountImage(Account account) throws Exception;

	public Account saveAccountImage(MultipartFile file, Account account) throws Exception;

	public Account deleteAccountImage(Account account) throws Exception;
	
//This Portion is relatd to rooms of the accounts
	
	//Service createRoom handle this two
	public Room createRoom(Room room);

	public Room getRoom(String name);
	
	public Room setRoomCreator(Account account,Room room);

	//This will find roomlist
	public RoomList findRoom(String name);

	public RoomList findBookmarkedRooms(String name);

	public RoomList findOwnedRooms(String name);

	//This part related to Institutions
	public Institutions addInstitution(Institutions institution);

	public Institutions deleteInstitution(Institutions institution,
			Account account);

	public Institutions searchInstitution(Institutions institutions);

	public Institutions addAccountToInstitution(Institutions institution,
			Account account);

	public InstitutionList getInstitutions(String username);

	public InstitutionList searchInstitutions(String check);

}
