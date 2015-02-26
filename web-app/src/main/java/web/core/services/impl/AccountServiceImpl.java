package web.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import web.core.services.exceptions.AccountExistsException;
import web.core.models.entities.Account;
import web.core.models.entities.Institutions;
import web.core.models.entities.Room;
import web.core.repositories.AccountRepo;
import web.core.services.AccountService;
import web.core.services.util.AccountList;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account verifyAccount(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room createRoom(String username, Room room) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomList findRoom(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountList findAccounts(String check) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomList findBookmarkedRooms(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomList findOwnedRooms(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountList getBookmarkedAccounts(String check) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account updateAccountInfo(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account updateAccountImage(MultipartFile file, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountImage(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Institutions addInstitution(Institutions institution, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteInstitution(Institutions institution, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account changeAccountPassword(Account account_old,
			Account account_new) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountList bookmarkAccount(String username_own,
			String username_follow) {
		// TODO Auto-generated method stub
		return null;
	}
}
