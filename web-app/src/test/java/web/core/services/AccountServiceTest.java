package web.core.services;

import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import web.core.models.entities.Account;
import web.core.models.entities.Institutions;
import web.core.models.entities.Room;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/test-config.xml")
public class AccountServiceTest {

	@Autowired
	private AccountService service;
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

		/*account2 = new Account();
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
		repo.saveAccountImage(image, account);*/
	}

}
