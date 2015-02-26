package web.core.repositories.jpa;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import web.core.models.entities.Account;
import web.core.models.entities.Institutions;
import web.core.models.entities.Room;
import web.core.repositories.AccountRepo;
import web.core.services.util.AccountList;
import web.core.services.util.InstitutionList;
import web.core.services.util.RoomList;

@Repository
public class JpaAccountRepo implements AccountRepo {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Account createAccount(Account account) {
		em.persist(account);
		return account;
	}

	@Override
	public Account findAccount(String username) {
		Query query = em
				.createQuery("SELECT a FROM Account a WHERE a.username=?1");
		query.setParameter(1, username);
		@SuppressWarnings("unchecked")
		List<Account> accounts = query.getResultList();
		if (accounts.size() == 0) {
			return null;
		} else {
			return accounts.get(0);
		}
	}

	@Override
	public Account verifyAccountWithUsernamePassword(Account account) {
		Query query = em
				.createQuery("SELECT a FROM Account a WHERE a.username=?1 and a.password=?2");
		query.setParameter(1, account.getUsername());
		query.setParameter(2, account.getPassword());
		@SuppressWarnings("unchecked")
		List<Account> accounts = query.getResultList();
		if (accounts.size() == 0) {
			return null;
		} else {
			return accounts.get(0);
		}
	}

	@Override
	public Account verifyAccountWithEmailPassword(Account account) {
		Query query = em
				.createQuery("SELECT a FROM Account a WHERE a.email=?1 and a.password=?2");
		query.setParameter(1, account.getEmail());
		query.setParameter(2, account.getPassword());
		@SuppressWarnings("unchecked")
		List<Account> accounts = query.getResultList();
		if (accounts.size() == 0) {
			return null;
		} else {
			return accounts.get(0);
		}
	}

	@Override
	public Room createRoom(Room room) {
		em.persist(room);
		return room;
	}

	@Override
	public Room setRoomCreator(Account account, Room room) {
		room.getAdmins().add(account);
		return room;
	}

	@Override
	public Account setBookmarkedRoom(Account account, Room room) {
		//TODO
		account.getBookmarkedroom().add(room);
		return account;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RoomList findRoom(String name) {
		Query query = em.createQuery("SELECT a FROM Room a "
				+ "WHERE a.roomname LIKE CONCAT('%',?1,'%')");
		query.setParameter(1, name);
		@SuppressWarnings({ "rawtypes" })
		List rooms = query.getResultList();
		if (rooms.size() == 0) {
			return null;
		} else {
			return new RoomList(rooms);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AccountList findAccounts(String check) {

		Query query = em.createQuery("SELECT a FROM Account a "
				+ "WHERE a.username LIKE CONCAT('%',?1,'%') "
				+"OR a.namefirst LIKE CONCAT('%',?1,'%') "
				+"OR a.namelast LIKE CONCAT('%',?1,'%')");
		query.setParameter(1, check);
		@SuppressWarnings({ "rawtypes" })
		List accounts = query.getResultList();;
		if (accounts.size() == 0) {
			return null;
		} else {
			return new AccountList(accounts);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RoomList findBookmarkedRooms(String name) {

		Query query = em.createQuery("SELECT markedroom from Account a inner join a.bookmarkedroom markedroom");
		@SuppressWarnings("rawtypes")
		List rooms = query.getResultList();;
		if (rooms.size() == 0) {
			return null;
		} else {
			return new RoomList(rooms);
		}	
		}

	@SuppressWarnings("unchecked")
	@Override
	public RoomList findOwnedRooms(String name) {
		Query query = em.createQuery("SELECT rooms from Account a inner join a.ownedroom rooms");
		@SuppressWarnings("rawtypes")
		List rooms = query.getResultList();;
		if (rooms.size() == 0) {
			return null;
		} else {
			return new RoomList(rooms);
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public AccountList getBookmarkedAccounts(String check) {
		Query query = em.createQuery("SELECT accounts from Account a inner join a.bookmarkedaccount accounts");
		@SuppressWarnings("rawtypes")
		List accounts = query.getResultList();;
		if (accounts.size() == 0) {
			return null;
		} else {
			return new AccountList(accounts);
		}	
	}

	@SuppressWarnings("deprecation")
	@Override
	public Account saveAccountImage(MultipartFile file, Account account) throws Exception {
		
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String rootPath = System.getProperty("user.home");
                File dir = new File(rootPath +File.separator +"web-app-images" +File.separator + account.getUsername());
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + "pp.jpg");
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();	 
                account.setImage(serverFile.toURL());
                } catch (Exception e) {
                    throw e;
                }	
                return account;
	}

	@Override
	public Account deleteAccountImage(Account account) throws Exception {
				
	            try {
	                File serverFile = new File(account.getImage().toString());
	                serverFile.delete();
	                account.setImage(null);
	                } catch (Exception e) {
	                    throw e;
	                }	
	                return account;
	}
	
	@Override
	public File getAccountImage(Account account) throws Exception {
		
		try {
             File serverFile = new File(account.getImage().toString());
     		return serverFile;
             } catch (Exception e) {
                 throw e;
             }			
	}

	@Override
	public Institutions addInstitution(Institutions institution) {
		em.persist(institution);
		return institution;
	}

	@Override
	public Institutions deleteInstitution(Institutions institution, Account account) {
		institution.getAccounts().remove(account);
		return institution;
	}
	
	@Override
	public Institutions searchInstitution(String inisname,Date start,Date end,String type,String description) {
		Query query = em
				.createQuery("SELECT a FROM Institutions a WHERE a.name=?1"
						+"AND a.start=?2"+"AND a.end=?3"+"AND a.type=?4"+"AND a.description=?5");
		query.setParameter(1, inisname);
		query.setParameter(2, start);
		query.setParameter(3, end);
		query.setParameter(4, type);
		query.setParameter(5, description);
		@SuppressWarnings("unchecked")
		List<Institutions> institutions = query.getResultList();
		if (institutions.size() == 0) {
			return null;
		} else {
			return institutions.get(0);
		}
	}
	
	@Override
	public Institutions addAccountToInstitution(Institutions institution, Account account) {
		institution.getAccounts().add(account);
		return institution;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public InstitutionList searchInstitutions(String check) {
		Query query = em.createQuery("SELECT a FROM Institutions a "
				+ "WHERE a.name LIKE CONCAT('%',?1,'%')");
		query.setParameter(1, check);
		@SuppressWarnings({ "rawtypes" })
		List institutions = query.getResultList();
		if (institutions.size() == 0) {
			return null;
		} else {
			return new InstitutionList(institutions);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public InstitutionList getInstitutions(String username) {
		
		Query query = em.createQuery("SELECT ins from Account a inner join a.institutions ins");
		@SuppressWarnings("rawtypes")
		List institutions = query.getResultList();;
		if (institutions.size() == 0) {
			return null;
		} else {
			return new InstitutionList(institutions);
		}	
	}
	
	@Override
	public Account changeAccountPassword(Account account_old,
			Account account_new) {
		account_old.setPassword(account_new.getPassword());
		return account_old;
	}

	@Override
	public Account bookmarkAccount(Account user_own,
			Account user_follow) {		
		user_own.getBookmarkedaccount().add(user_follow);
		return user_own;
	}

	@Override
	public Account updateAccountFirstName(Account account,Account updatedaccount) {
		account.setNamefirst(updatedaccount.getNamefirst());
		return account;
	}

	@Override
	public Account updateAccountLastName(Account account,Account updatedaccount) {
		account.setNamelast(updatedaccount.getNamelast());
		return account;
	}

	@Override
	public Account updateAccountCity(Account account,Account updatedaccount) {
		account.setCity(updatedaccount.getCity());
		return account;
	}

	@Override
	public Account updateAccountCountry(Account account,Account updatedaccount) {
		account.setCountry(updatedaccount.getCountry());
		return account;
	}

	@Override
	public Account updateAccountAbount(Account account,Account updatedaccount) {
		account.setAbout(updatedaccount.getAbout());
		return account;
	}
}
