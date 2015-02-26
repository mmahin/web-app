package web.core.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import web.core.models.entities.Account;
import web.core.models.entities.Institutions;
import web.core.models.entities.Room;
import web.core.services.util.AccountList;
import web.core.services.util.InstitutionList;
import web.core.services.util.RoomList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/test-config.xml")
public class AccountRepoTest {

	@Autowired
	private AccountRepo repo;
	private Account account, account2;
	private Room room;
	private Institutions institution;

	@Before
	@Transactional
	@Rollback(false)
	public void setup() throws Exception {
		account = new Account();
		account.setPassword("test");
		account.setNamefirst("Md");
		account.setNamelast("Mahin");
		account.setUsername("test");
		account.setEmail("test");
		account.setGender("test");
		Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		account.setBirthday(testBirthday);
		account.setCity("test");
		account.setCountry("test");
		repo.createAccount(account);

		account2 = new Account();
		account2.setPassword("test2");
		account2.setNamefirst("Md");
		account2.setNamelast("Ruhin");
		account2.setUsername("test2");
		account2.setEmail("test2");
		account2.setGender("test2");
		account2.setBirthday(testBirthday);
		account2.setCity("test2");
		account2.setCountry("test2");
		repo.createAccount(account2);
		repo.bookmarkAccount(account, account2);

		room = new Room();
		room.setRoomname("Test Title");
		room.setDescription("test");
		List<Account> listA = new ArrayList<Account>();
		listA.add(account);
		room.setAdmins(listA);
		repo.createRoom(room);
		repo.setBookmarkedRoom(account, room);

		institution = new Institutions();
		institution.setName("NSTU");
		institution.setType("EDU");
		institution.setDescription("Studid in CSTE");
		Date startDate = new Date(new DateTime(2009, 8, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		Date endDate = new Date(new DateTime(2014, 5, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		institution.setStart(startDate);
		institution.setEnd(endDate);
		repo.addInstitution(institution);
		repo.addAccountToInstitution(institution, account);
		
		FileInputStream fis = new FileInputStream("C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fis);
		repo.saveAccountImage(image, account);
	}

	@Test
	@Transactional
	public void testFindAccount() {
		Account account = repo.findAccount(this.account.getUsername());
		assertNotNull(account);
		assertEquals(account.getUsername(), "test");
		assertEquals(account.getPassword(), "test");
	}

	@Test
	@Transactional
	public void testVerifyAccountWithUsernamePassword() {
		Account account = repo.verifyAccountWithUsernamePassword(this.account);
		assertNotNull(account);
	}

	@Test
	@Transactional
	public void testVerifyAccountWithEmailPassword() {
		Account account = repo.verifyAccountWithUsernamePassword(this.account);
		assertNotNull(account);
	}

	@Test
	@Transactional
	public void testSetRoomCreator() {
		Room room = repo.setRoomCreator(this.account, this.room);
		assertNotNull(room.getAdmins());

	}

	@Test
	@Transactional
	public void testFindRoom() {
		RoomList room = repo.findRoom("Test Title");
		assertNotNull(room);

	}

	@Test
	@Transactional
	public void testFindAccounts() {
		AccountList accounts = repo.findAccounts("test");
		assertNotNull(accounts);

	}

	@Test
	@Transactional
	public void testFindBookmarkedRooms() {
		RoomList rooms = repo.findBookmarkedRooms("test");
		assertNotNull(rooms);
		assertEquals(room.getRoomname(), "Test Title");

	}

	@Test
	@Transactional
	public void testFindOwnedRooms() {
		RoomList rooms = repo.findOwnedRooms("test");
		assertNotNull(rooms);
		assertEquals(room.getRoomname(), "Test Title");

	}

	@Test
	@Transactional
	public void testFindBookmarkedAccounts() {
		AccountList accounts = repo.getBookmarkedAccounts("test");
		assertNotNull(accounts);
		assertEquals(account2.getUsername(), "test2");

	}

	@Test
	@Transactional
	public void testUpdateAccountFirstName() {
		Account updatedaccount = new Account();
		updatedaccount.setNamefirst("Mohammed");
		Account accountnew = repo.updateAccountFirstName(account,
				updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getNamefirst(), "Mohammed");

	}

	@Test
	@Transactional
	public void testUpdateAccountLastName() {
		Account updatedaccount = new Account();
		updatedaccount.setNamelast("Mahin1");
		Account accountnew = repo
				.updateAccountLastName(account, updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getNamelast(), "Mahin1");

	}

	@Test
	@Transactional
	public void testUpdateAccountCity() {
		Account updatedaccount = new Account();
		updatedaccount.setCity("Dhaka");
		Account accountnew = repo.updateAccountCity(account, updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getCity(), "Dhaka");

	}

	@Test
	@Transactional
	public void testUpdateAccountCountry() {
		Account updatedaccount = new Account();
		updatedaccount.setCountry("Bangladesh");
		updatedaccount.setAbout("I'm Mahin");
		Account accountnew = repo.updateAccountCountry(account, updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getCountry(), "Bangladesh");

	}

	@Test
	@Transactional
	public void testUpdateAccountAbout() {
		Account updatedaccount = new Account();
		updatedaccount.setAbout("I'm Mahin");
		Account accountnew = repo.updateAccountAbount(account, updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getAbout(), "I'm Mahin");

	}

	@Test
	@Transactional
	public void testSearchInstitutionOfAccount() {
		Institutions institution = repo.searchInstitution(
				this.institution.getName(), this.institution.getStart(),
				this.institution.getEnd(), this.institution.getType(),
				this.institution.getDescription());
		assertNotNull(institution);
		assertEquals(institution.getName(), "NSTU");
	}

	@Test
	@Transactional
	public void getInstitution() {
		InstitutionList institutions = repo.getInstitutions("test");
		assertNotNull(institutions);
		assertEquals(institution.getName(), "NSTU");
	}

	@Test
	@Transactional
	public void testgetAccountsInstitutions() {
		InstitutionList institutions = repo.getInstitutions("test");
		assertNotNull(institutions);
		assertEquals(institution.getName(), "NSTU");

	}

	@Test
	@Transactional
	public void testSearchInstitutions() {
		InstitutionList institutions = repo.searchInstitutions("NSTU");
		assertNotNull(institutions);
	}

	@Test
	@Transactional
	public void testDeletInstitutions() {
		Institutions institutions = repo
				.deleteInstitution(institution, account);
		assertNotNull(institutions);
	}
	

	@Test
	@Transactional
	public void testDeleteAccountImage() throws Exception {
		
		Account accountnew = repo
				.deleteAccountImage(account);
		assertEquals(accountnew.getImage().toString(), null);		
	}
	
	@Test
	@Transactional
	public void testgetAccountImage() throws Exception {
		
		File file = repo
				.getAccountImage(account);
		assertNotNull(file);
	}
}
