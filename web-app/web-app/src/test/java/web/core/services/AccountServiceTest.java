package web.core.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
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
import web.core.repositories.AccountRepo;
import web.core.services.util.AccountList;
import web.core.services.util.InstitutionList;
import web.core.services.util.RoomList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/test-config.xml")
public class AccountServiceTest {

	@Autowired
	private AccountService service;
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
		service.createAccount(account);

		room = new Room();
		room.setRoomname("Test Title");
		room.setDescription("test");
		service.createRoom("test",room);
		repo.setBookmarkedRoom(account, room);

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
		service.createAccount(account2);
		service.bookmarkAccount("test", "test2");
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
		service.addInstitution(institution,"test");
		
		FileInputStream fis = new FileInputStream("C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fis);
		service.updateAccountImage(image, "test");
	}
	
	@Test
	@Transactional
	public void testFindAccount() {
		Account account = service.findAccount(this.account.getUsername());
		assertNotNull(account);
		assertEquals(account.getUsername(), "test");
		assertEquals(account.getPassword(), "test");
	}
	
	@Test
	@Transactional
	public void testVerifyAccountWithUsernamePassword() {
		Account test=new Account();
		test.setUsername("test");
		test.setPassword("test");
		Account account = service.verifyAccount(test);
		assertNotNull(account);
	}

	@Test
	@Transactional
	public void testVerifyAccountWithEmailPassword() {
		Account test=new Account();
		test.setEmail("test");
		test.setPassword("test");
		Account account = service.verifyAccount(test);
		assertNotNull(account);
	}
	
	@Test
	@Transactional
	public void testVerifyAccountFailure() {
		Account test=new Account();
		test.setEmail("test1");
		test.setPassword("test");
		service.verifyAccount(test);		
	}
	
	@Test
	@Transactional
	public void testCreateRoomFailure() {
		service.createRoom("test", room);		
	}
	
	@Test
	@Transactional
	public void testFindRoom() {
		RoomList room = service.findRoom("Test Title");
		assertNotNull(room);
	}
	
	@Test
	@Transactional
	public void testFindRoomFailure() {
		service.findRoom("now");
	}
	
	@Test
	@Transactional
	public void testFindAccounts() {
		AccountList accounts = service.findAccounts("test");
		assertNotNull(accounts);
	}
	
	@Test
	@Transactional
	public void testFindAccountsFailure() {
		service.findAccounts("now");
	}
	
	@Test
	@Transactional
	public void testFindBookmarkedRooms() {
		RoomList room = service.findBookmarkedRooms("test");
		assertNotNull(room);
	}
	
	@Test
	@Transactional
	public void testFindBookmarkedRoomsFailure() {
		service.findBookmarkedRooms("now");
	}
	
	@Test
	@Transactional
	public void testFindOwnedRooms() {
		RoomList room = service.findOwnedRooms("test");
		assertNotNull(room);
	}
	
	@Test
	@Transactional
	public void testFindOwnedRoomsFailure() {
		service.findOwnedRooms("now");
	}
	
	@Test
	@Transactional
	public void testFindBookmarkedAccounts() {
		AccountList accounts = service.getBookmarkedAccounts("test");
		assertNotNull(accounts);
	}
	
	@Test
	@Transactional
	public void testFindBookmarkedAccountsFailure() {
		service.getBookmarkedAccounts("now");
	}
	
	@Test
	@Transactional
	public void testGetInstitutions() {
		InstitutionList institutionList = service.getInstitutions("test");
		assertNotNull(institutionList);
	}
	
	@Test
	@Transactional
	public void testGetInstitutionsFailure() {
		service.getInstitutions("5");
	}
	
	@Test
	@Transactional
	public void testSearchInstitutions() {
		InstitutionList institutionList = service.searchInstitutions("NSTU");
		assertNotNull(institutionList);
	}
	
	@Test
	@Transactional
	public void testSearchInstitutionsFailure() {
		service.searchInstitutions("5");
	}
	
	@Test
	@Transactional
	public void testDeleteInstitutions() {
		String msg = service.deleteInstitution(institution, "test");
		assertEquals("Success", msg);
	}


	@Test
	@Transactional
	public void testgetAccountImage() throws Exception {

		File file = service.getAccountImage("test");
		assertNotNull(file);
	}
	@Test
	@Transactional
	public void testUpdateAccountFirstName() {
		Account updatedaccount = new Account();
		updatedaccount.setNamefirst("Mohammed");
		updatedaccount.setUsername("test");
		Account accountnew = service.updateAccountInfo(updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getNamefirst(), "Mohammed");

	}

	@Test
	@Transactional
	public void testUpdateAccountLastName() {
		Account updatedaccount = new Account();
		updatedaccount.setNamelast("Mahin1");
		updatedaccount.setUsername("test");
		Account accountnew = service.updateAccountInfo(updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getNamelast(), "Mahin1");

	}

	@Test
	@Transactional
	public void testUpdateAccountCity() {
		Account updatedaccount = new Account();
		updatedaccount.setCity("Dhaka");
		updatedaccount.setUsername("test");
		Account accountnew = service.updateAccountInfo(updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getCity(), "Dhaka");

	}

	@Test
	@Transactional
	public void testUpdateAccountCountry() {
		Account updatedaccount = new Account();
		updatedaccount.setCountry("Bangladesh");
		updatedaccount.setUsername("test");
		Account accountnew = service.updateAccountInfo(updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getCountry(), "Bangladesh");

	}

	@Test
	@Transactional
	public void testUpdateAccountAbout() {
		Account updatedaccount = new Account();
		updatedaccount.setAbout("I'm Mahin");
		updatedaccount.setUsername("test");
		Account accountnew = service.updateAccountInfo(updatedaccount);
		assertNotNull(accountnew);
		assertEquals(accountnew.getAbout(), "I'm Mahin");
		assertEquals(accountnew.getNamefirst(), "Mahin");
		assertEquals(accountnew.getEmail(), "test");
	}
}
