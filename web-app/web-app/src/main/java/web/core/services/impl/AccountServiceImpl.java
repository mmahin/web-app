package web.core.services.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import web.core.services.exceptions.AccountDoesNotExistException;
import web.core.services.exceptions.AccountExistsException;
import web.core.services.exceptions.AccountImageNotFoundException;
import web.core.services.exceptions.AccountNotMatchingException;
import web.core.services.exceptions.AccountUpdateFailureException;
import web.core.services.exceptions.RoomDoesNotExistException;
import web.core.services.exceptions.RoomExistsException;
import web.core.services.exceptions.InstitutionsEmptyException;
import web.core.models.entities.Account;
import web.core.models.entities.Institutions;
import web.core.models.entities.Room;
import web.core.repositories.AccountRepo;
import web.core.services.AccountService;
import web.core.services.util.AccountList;
import web.core.services.util.InstitutionList;
import web.core.services.util.RoomList;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepo accountRepo;

	@Override
	public Account createAccount(Account data) {
		Account account = accountRepo.findAccount(data.getUsername());
		if (account != null) {
			throw new AccountExistsException();
		}
		return accountRepo.createAccount(data);
	}

	@Override
	public Account findAccount(String username) {
		return accountRepo.findAccount(username);
	}

	@Override
	public Account verifyAccount(Account data) {
		Account account = new Account();
		if (data.getUsername() != null) {
			account = accountRepo.verifyAccountWithUsernamePassword(data);
		} else {
			account = accountRepo.verifyAccountWithEmailPassword(data);
		}
		if (account == null) {
			throw new AccountNotMatchingException();
		}
		return account;
	}

	@Override
	public Room createRoom(String username, Room data) {
		Room room = accountRepo.getRoom(data.getRoomname());
		Room createdRoom = new Room();
		if (room != null) {
			throw new RoomExistsException();
		} else {
			createdRoom = accountRepo.createRoom(data);
		}
		Account account = accountRepo.findAccount(username);
		accountRepo.setRoomCreator(account, createdRoom);
		return createdRoom;
	}

	@Override
	public RoomList findRoom(String name) {
		RoomList rooms = accountRepo.findRoom(name);
		if (rooms == null) {
			throw new RoomDoesNotExistException();
		}
		return rooms;
	}

	@Override
	public AccountList findAccounts(String check) {
		AccountList accounts = accountRepo.findAccounts(check);
		if (accounts == null) {
			throw new AccountDoesNotExistException();
		}
		return accounts;
	}

	@Override
	public RoomList findBookmarkedRooms(String name) {
		RoomList rooms = accountRepo.findBookmarkedRooms(name);
		if (rooms == null) {
			throw new RoomDoesNotExistException();
		}
		return rooms;
	}

	@Override
	public RoomList findOwnedRooms(String name) {
		RoomList rooms = accountRepo.findOwnedRooms(name);
		if (rooms == null) {
			throw new RoomDoesNotExistException();
		}
		return rooms;
	}

	@Override
	public AccountList getBookmarkedAccounts(String check) {
		AccountList accounts = accountRepo.getBookmarkedAccounts(check);
		if (accounts == null) {
			throw new AccountDoesNotExistException();
		}
		return accounts;
	}

	@Override
	public Account updateAccountInfo(Account check) {
		Account account=new Account();
		if(account.getAbout()!=null){
			account=accountRepo.updateAccountAbount(check);
		}
		if(account.getNamefirst()!=null){
			account=accountRepo.updateAccountFirstName(check);
		}
		if(account.getNamelast()!=null){
			account=accountRepo.updateAccountLastName(check);
		}
		if(account.getCity()!=null){
			account=accountRepo.updateAccountCity(check);
		}
		if(account.getCountry()!=null){
			account=accountRepo.updateAccountCountry(check);
		}
		if(account==null){
			throw new AccountUpdateFailureException();
		}
		return account;
	}

	@SuppressWarnings("unused")
	@Override
	public Account updateAccountImage(MultipartFile file, String username) throws Exception {
		Account account=accountRepo.findAccount(username);
        File img=accountRepo.getAccountImage(account);
        if(img!=null){
        	accountRepo.deleteAccountImage(account);
        	Account accountnew=accountRepo.saveAccountImage(file, account);
        	return accountnew;
        }
        else if (img==null) {
        	Account accountnew=accountRepo.saveAccountImage(file, account);
        	return accountnew; 	
        }
        else {
			throw new AccountUpdateFailureException();
		}
	}

	@Override
	public File getAccountImage(String username) throws Exception {
		Account account=accountRepo.findAccount(username);
		File img=accountRepo.getAccountImage(account);
		if(img==null){
			throw new AccountImageNotFoundException();
		}
		return img;
	}

	@Override
	public Institutions addInstitution(Institutions check, String username) {
		Institutions checkinstitution=accountRepo.searchInstitution(check);
		if(checkinstitution!=null){
			Account account=accountRepo.findAccount(username);
			accountRepo.addAccountToInstitution(checkinstitution, account);
			return checkinstitution;			
		}
		else{
			Institutions institution=accountRepo.addInstitution(check);
			Account account=accountRepo.findAccount(username);
			accountRepo.addAccountToInstitution(institution, account);
			return institution;
		}
	}

	@Override
	public String deleteInstitution(Institutions institution, String username) {
		Account account=accountRepo.findAccount(username);
		Institutions institutions=accountRepo.deleteInstitution(institution, account);
		if(institutions.getAccounts().contains(account)){
			return "Failed";
		}
		else {
			return "Success";
		}
	}

	@Override
	public Account changeAccountPassword(Account account_old,
			Account account_new) {
		Account account=accountRepo.changeAccountPassword(account_old, account_new);
		return account;
	}

	@Override
	public AccountList bookmarkAccount(String username_own,
			String username_follow) {
		Account account_own = accountRepo.findAccount(username_own);
		Account account_follow = accountRepo.findAccount(username_follow);
		accountRepo.bookmarkAccount(account_own, account_follow);
		AccountList accounts = accountRepo.getBookmarkedAccounts(username_own);
		if (accounts == null) {
			throw new AccountDoesNotExistException();
		}
		return accounts;
	}

	@Override
	public InstitutionList getInstitutions(String username) {
		InstitutionList institutions=accountRepo.getInstitutions(username);
		if(institutions==null){
			throw new InstitutionsEmptyException();
		}
		return institutions;
	}

	@Override
	public InstitutionList searchInstitutions(String check) {
		InstitutionList institutions=accountRepo.searchInstitutions(check);
		if(institutions==null){
			throw new InstitutionsEmptyException();
		}
		return institutions;
	}
}
