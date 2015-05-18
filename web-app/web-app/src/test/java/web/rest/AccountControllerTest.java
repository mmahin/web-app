package web.rest;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import web.core.services.util.AccountList;
import web.core.services.util.InstitutionList;
import web.core.services.util.RoomList;
import web.core.services.exceptions.AccountAndInstitotionsUpdateFailureException;
import web.core.services.exceptions.AccountExistsException;
import web.core.services.exceptions.AccountImageNotFoundException;
import web.core.services.exceptions.AccountNotMatchingException;
import web.core.services.exceptions.AccountUpdateFailureException;
import web.core.services.exceptions.RoomExistsException;
import web.core.models.entities.Account;
import web.core.models.entities.Institutions;
import web.core.models.entities.Room;
import web.core.services.AccountService;
import web.rest.exceptions.NotAcceptableException;
import web.rest.mvc.AccountController;

public class AccountControllerTest {
	@InjectMocks
	private AccountController controller;
	@Mock
	private AccountService service;
	private MockMvc mockMvc;
	private ArgumentCaptor<Account> accountCaptor;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		accountCaptor = ArgumentCaptor.forClass(Account.class);

	}

	// signup test
	@Test
	public void createAccountNonExistingUsernameAndEmail() throws Exception {
		Account createdAccount = new Account();
		createdAccount.setId(1L);
		createdAccount.setPassword("test");
		createdAccount.setNamefirst("test");
		createdAccount.setNamelast("test");
		createdAccount.setUsername("test");
		createdAccount.setEmail("test");
		createdAccount.setGender("test");
		Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		createdAccount.setBirthday(testBirthday);
		createdAccount.setCity("test");
		createdAccount.setCountry("test");

		when(service.createAccount(any(Account.class))).thenReturn(
				createdAccount);
		mockMvc.perform(
				post("/accounts/signup")
						.content(
								"{\"password\":\"test\",\"namefirst\":\"test\",\"namelast\":\"test\",\"username\":\"test\",\"birthday\":\"2007-9-23\",\"gender\":\"test\",\"city\":\"test\",\"country\":\"test\",\"email\":\"test\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(
						header().string("Location", endsWith("/accounts/test")))
				.andExpect(
						jsonPath("$.username", is(createdAccount.getUsername())))
				.andExpect(status().isCreated());
		verify(service).createAccount(accountCaptor.capture());
		String password = accountCaptor.getValue().getPassword();
		assertEquals("test", password);
	}

	@Test
	public void createAccountExistingUsernameAndEmail() throws Exception {
		
		when(service.createAccount(any(Account.class))).thenThrow(
				new AccountExistsException());
		mockMvc.perform(
				post("/accounts/signup")
						.content(
								"{\"password\":\"test\",\"namefirst\":\"test\",\"namelast\":\"test\",\"username\":\"test\",\"birthday\":\"2007-9-23\",\"gender\":\"test\",\"city\":\"test\",\"country\":\"test\",\"email\":\"test\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict()).andDo(print());
	}

	// login test using username and password,email=null
	@Test
	public void getAccountMatchingUsernamePassword() throws Exception {
		Account verifyAccount = new Account();
		verifyAccount.setId(1L);
		verifyAccount.setPassword("test");
		verifyAccount.setNamefirst("test");
		verifyAccount.setNamelast("test");
		verifyAccount.setUsername("test");
		verifyAccount.setEmail("test");
		verifyAccount.setGender("test");
		Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		verifyAccount.setBirthday(testBirthday);
		verifyAccount.setCity("test");
		verifyAccount.setCountry("test");

		when(service.verifyAccount(any(Account.class))).thenReturn(
				verifyAccount);
		mockMvc.perform(
				post("/accounts/login")
						.content(
								"{\"password\":\"test\",\"username\":\"test\",\"email\":\"\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(
						header().string("Location", endsWith("/accounts/test")))
				.andExpect(
						jsonPath("$.username", is(verifyAccount.getUsername())))
				.andExpect(jsonPath("$.email", is(verifyAccount.getEmail())))
				.andExpect(status().isOk());
		verify(service).verifyAccount(accountCaptor.capture());
		String password = accountCaptor.getValue().getPassword();
		assertEquals("test", password);

	}

	@Test
	public void getAccountNonMatchingUsernamePassword() throws Exception {

		when(service.verifyAccount(any(Account.class))).thenThrow(
				new AccountNotMatchingException());
		mockMvc.perform(
				post("/accounts/login")
						.content(
								"{\"password\":\"test\",\"username\":\"test\",\"email\":\"\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotAcceptable());

	}

	// login test using email and password,username=null
	@Test
	public void getAccountMatchingEmailPassword() throws Exception {
		Account verifyAccount = new Account();
		verifyAccount.setId(1L);
		verifyAccount.setPassword("test");
		verifyAccount.setNamefirst("test");
		verifyAccount.setNamelast("test");
		verifyAccount.setUsername("test");
		verifyAccount.setEmail("test");
		verifyAccount.setGender("test");
		Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		verifyAccount.setBirthday(testBirthday);
		verifyAccount.setCity("test");
		verifyAccount.setCountry("test");

		when(service.verifyAccount(any(Account.class))).thenReturn(
				verifyAccount);
		mockMvc.perform(
				post("/accounts/login")
						.content(
								"{\"username\":\"\",\"email\":\"test\",\"password\":\"test\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(
						header().string("Location", endsWith("/accounts/test")))
				.andExpect(
						jsonPath("$.username", is(verifyAccount.getUsername())))
				.andExpect(jsonPath("$.email", is(verifyAccount.getEmail())))
				.andExpect(status().isOk());
		verify(service).verifyAccount(accountCaptor.capture());
		String password = accountCaptor.getValue().getPassword();
		assertEquals("test", password);

	}

	@Test
	public void getAccountNonMatchingEmailPassword() throws Exception {

		when(service.verifyAccount(any(Account.class))).thenThrow(
				new AccountNotMatchingException());
		mockMvc.perform(
				post("/accounts/login")
						.content(
								"{\"password\":\"test\",\"username\":\"\",\"email\":\"test\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotAcceptable());

	}

	// Create Group test
	@Test
	public void createNonExistingRoom() throws Exception {
		Room createRoom = new Room();
		createRoom.setRoomid(1L);
		createRoom.setRoomname("Test Title");
		createRoom.setDescription("test");
		when(service.createRoom(eq("test"), any(Room.class))).thenReturn(
				createRoom);
		mockMvc.perform(
				post("/accounts/test/createroom")
						.content(
								"{\"roomname\":\"Test Title\",\"description\":\"Test\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.roomname", is("Test Title")))
				.andExpect(
						jsonPath("$.links[*].href",
								hasItem(endsWith("/rooms/1"))))
				.andExpect(header().string("Location", endsWith("/rooms/1")))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void createRoomExistingRoomName() throws Exception {
		when(service.createRoom(eq("test"), any(Room.class))).thenThrow(
				new RoomExistsException());
		mockMvc.perform(
				post("/accounts/test/createroom")
						.content(
								"{\"roomname\":\"Test Title\",\"description\":\"Test\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict()).andDo(print());
	}

	// Search for rooms
	@SuppressWarnings("unchecked")
	@Test
	public void findExistingRoomByName() throws Exception {
		List<Room> list = new ArrayList<Room>();
		Room roomA = new Room();
		roomA.setRoomid(1L);
		roomA.setRoomname("Title A");
		list.add(roomA);
		Room roomB = new Room();
		roomB.setRoomid(2L);
		roomB.setRoomname("Title B");
		list.add(roomB);
		RoomList roomList = new RoomList(list);
		when(service.findRoom("room")).thenReturn(roomList);
		mockMvc.perform(get("/accounts/search/rooms/room"))
				.andExpect(
						jsonPath(
								"$.rooms[*].roomname",
								hasItems(endsWith("Title A"),
										endsWith("Title B"))))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void findNonExistingRoomByName() throws Exception {
		List<Room> list = new ArrayList<Room>();
		Room roomA = new Room();
		roomA.setRoomid(1L);
		roomA.setRoomname("Title A");
		list.add(roomA);
		Room roomB = new Room();
		roomB.setRoomid(2L);
		roomB.setRoomname("Title B");
		list.add(roomB);
		RoomList roomList = new RoomList(list);
		when(service.findRoom("room")).thenReturn(roomList);
		mockMvc.perform(get("/accounts/search/room/room")).andDo(print())
				.andExpect(status().isNotFound());
	}

	// Search accounts using username/name/email
	@SuppressWarnings("unchecked")
	@Test
	public void findNonExistingAccountByNameUsernameEmail() throws Exception {
		List<Account> list = new ArrayList<Account>();
		Account accountA = new Account();
		accountA.setId(1L);
		accountA.setPassword("testA");
		accountA.setNamefirst("testA");
		accountA.setNamelast("testA");
		accountA.setUsername("testA");
		accountA.setEmail("testA");
		accountA.setGender("testA");
		Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		accountA.setBirthday(testBirthday);
		accountA.setCity("testA");
		accountA.setCountry("testA");
		list.add(accountA);
		Account accountB = new Account();
		accountB.setId(2L);
		accountB.setPassword("testB");
		accountB.setNamefirst("testB");
		accountB.setNamelast("testB");
		accountB.setUsername("testB");
		accountB.setEmail("testB");
		accountB.setGender("testB");
		accountB.setBirthday(testBirthday);
		accountB.setCity("testB");
		accountB.setCountry("testB");
		list.add(accountB);
		AccountList accountList = new AccountList(list);
		when(service.findAccounts("account")).thenReturn(accountList);
		mockMvc.perform(get("/accounts/search/accounts/account"))
				.andExpect(
						jsonPath("$.accounts[*].username",
								hasItems(endsWith("testA"), endsWith("testB"))))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void findExistingAccountByNameUsernameEmail() throws Exception {
		List<Account> list = new ArrayList<Account>();
		Account accountA = new Account();
		accountA.setId(1L);
		accountA.setPassword("testA");
		accountA.setNamefirst("testA");
		accountA.setNamelast("testA");
		accountA.setUsername("testA");
		accountA.setEmail("testA");
		accountA.setGender("testA");
		Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		accountA.setBirthday(testBirthday);
		accountA.setCity("testA");
		accountA.setCountry("testA");
		list.add(accountA);
		Account accountB = new Account();
		accountB.setId(2L);
		accountB.setPassword("testB");
		accountB.setNamefirst("testB");
		accountB.setNamelast("testB");
		accountB.setUsername("testB");
		accountB.setEmail("testB");
		accountB.setGender("testB");
		accountB.setBirthday(testBirthday);
		accountB.setCity("testB");
		accountB.setCountry("testB");
		list.add(accountB);
		AccountList accountList = new AccountList(list);
		when(service.findAccounts("account")).thenReturn(accountList);
		mockMvc.perform(get("/accounts/search/accounts/account"))
				.andDo(print()).andExpect(status().isNotFound());
	}

	// Get user bookmarked rooms
	@SuppressWarnings("unchecked")
	@Test
	public void getExistingBookmarkedRooms() throws Exception {

		List<Room> list = new ArrayList<Room>();
		Room roomA = new Room();
		roomA.setRoomid(1L);
		roomA.setRoomname("Title A");
		list.add(roomA);
		Room roomB = new Room();
		roomB.setRoomid(2L);
		roomB.setRoomname("Title B");
		list.add(roomB);
		RoomList roomList = new RoomList(list);
		when(service.findBookmarkedRooms("test")).thenReturn(roomList);
		mockMvc.perform(get("/accounts/test/bookmarkedrooms"))
				.andExpect(
						jsonPath(
								"$.rooms[*].roomname",
								hasItems(endsWith("Title A"),
										endsWith("Title B"))))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void getNonExistingBookmarkedRooms() throws Exception {

		List<Room> list = new ArrayList<Room>();
		Room roomA = new Room();
		roomA.setRoomid(1L);
		roomA.setRoomname("Title A");
		list.add(roomA);
		Room roomB = new Room();
		roomB.setRoomid(2L);
		roomB.setRoomname("Title B");
		list.add(roomB);
		RoomList roomList = new RoomList(list);
		when(service.findBookmarkedRooms("test")).thenReturn(roomList);
		mockMvc.perform(get("/accounts/test/bookmarkedrooms")).andDo(print())
				.andExpect(status().isNotFound());
	}

	// get User owened rooms

	@SuppressWarnings("unchecked")
	@Test
	public void getExistingOwnedRooms() throws Exception {

		List<Room> list = new ArrayList<Room>();
		Room roomA = new Room();
		roomA.setRoomid(1L);
		roomA.setRoomname("Title A");
		list.add(roomA);
		Room roomB = new Room();
		roomB.setRoomid(2L);
		roomB.setRoomname("Title B");
		list.add(roomB);
		RoomList roomList = new RoomList(list);
		when(service.findOwnedRooms("test")).thenReturn(roomList);
		mockMvc.perform(get("/accounts/test/ownedrooms"))
				.andExpect(
						jsonPath(
								"$.rooms[*].roomname",
								hasItems(endsWith("Title A"),
										endsWith("Title B"))))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void getNonExistingOwnedRooms() throws Exception {

		List<Room> list = new ArrayList<Room>();
		Room roomA = new Room();
		roomA.setRoomid(1L);
		roomA.setRoomname("Title A");
		list.add(roomA);
		Room roomB = new Room();
		roomB.setRoomid(2L);
		roomB.setRoomname("Title B");
		list.add(roomB);
		RoomList roomList = new RoomList(list);
		when(service.findOwnedRooms("test")).thenReturn(roomList);
		mockMvc.perform(get("/accounts/test/ownedrooms")).andDo(print())
				.andExpect(status().isNotFound());
	}

	// Get bookmarked accounts
	@SuppressWarnings("unchecked")
	@Test
	public void getExistingBookmarkedAccounts() throws Exception {
		List<Account> list = new ArrayList<Account>();
		Account accountA = new Account();
		accountA.setId(1L);
		accountA.setPassword("testA");
		accountA.setNamefirst("testA");
		accountA.setNamelast("testA");
		accountA.setUsername("testA");
		accountA.setEmail("testA");
		accountA.setGender("testA");
		Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		accountA.setBirthday(testBirthday);
		accountA.setCity("testA");
		accountA.setCountry("testA");
		list.add(accountA);
		Account accountB = new Account();
		accountB.setId(2L);
		accountB.setPassword("testB");
		accountB.setNamefirst("testB");
		accountB.setNamelast("testB");
		accountB.setUsername("testB");
		accountB.setEmail("testB");
		accountB.setGender("testB");
		accountB.setBirthday(testBirthday);
		accountB.setCity("testB");
		accountB.setCountry("testB");
		list.add(accountB);
		AccountList accountList = new AccountList(list);
		when(service.getBookmarkedAccounts("account")).thenReturn(accountList);
		mockMvc.perform(get("/accounts/test/bookmarkedaccounts"))
				.andDo(print())
				.andExpect(
						jsonPath("$.accounts[*].username",
								hasItems(endsWith("testA"), endsWith("testB"))))
				.andExpect(status().isOk());
	}

	@Test
	public void getNonExistingBookmarkedAccounts() throws Exception {
		List<Account> list = new ArrayList<Account>();
		Account accountA = new Account();
		accountA.setId(1L);
		accountA.setPassword("testA");
		accountA.setNamefirst("testA");
		accountA.setNamelast("testA");
		accountA.setUsername("testA");
		accountA.setEmail("testA");
		accountA.setGender("testA");
		Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		accountA.setBirthday(testBirthday);
		accountA.setCity("testA");
		accountA.setCountry("testA");
		list.add(accountA);
		Account accountB = new Account();
		accountB.setId(2L);
		accountB.setPassword("testB");
		accountB.setNamefirst("testB");
		accountB.setNamelast("testB");
		accountB.setUsername("testB");
		accountB.setEmail("testB");
		accountB.setGender("testB");
		accountB.setBirthday(testBirthday);
		accountB.setCity("testB");
		accountB.setCountry("testB");
		list.add(accountB);
		AccountList accountList = new AccountList(list);
		when(service.getBookmarkedAccounts("account")).thenReturn(accountList);
		mockMvc.perform(get("/accounts/test/bookmarkedaccounts"))
				.andDo(print()).andExpect(status().isNotFound());
	}

	// Update account Image
	@Test
	public void updateAccountImage() throws Exception{
		Account updateAccount = new Account();
		updateAccount.setPassword("test");
		updateAccount.setNamefirst("test");
		updateAccount.setNamelast("test");
		updateAccount.setUsername("test");

		updateAccount.setEmail("test");
		updateAccount.setCity("test");
		updateAccount.setCountry("test");
		updateAccount.setAbout("test");
	    URL url = new URL("file:///C:/Users/Public/Pictures/Sample%20Pictures/Penguins.jpg");
		updateAccount.setImage(url);

		FileInputStream fis = new FileInputStream("C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fis);

              
		  HashMap<String, String> contentTypeParams = new HashMap<String, String>();
        contentTypeParams.put("boundary", "265001916915724");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        when(service.updateAccountImage(image,"test")).thenReturn(
				updateAccount);
		mockMvc.perform(
				MockMvcRequestBuilders.fileUpload("/accounts/profile/test/updateimage")
				.file(image)		
                    .contentType(mediaType))
				.andDo(print())
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void updateAccountImageFailure() throws Exception{

		FileInputStream fis = new FileInputStream("C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg");
        MockMultipartFile image = new MockMultipartFile("image", fis);
        
        when(service.updateAccountImage(image,"test")).thenThrow(
				new AccountUpdateFailureException());
                   
		  HashMap<String, String> contentTypeParams = new HashMap<String, String>();
        contentTypeParams.put("boundary", "265001916915724");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

		mockMvc.perform(
				MockMvcRequestBuilders.fileUpload("/accounts/profile/test/updateimage")
				.file(image)		
                    .contentType(mediaType))
				.andDo(print())
				.andExpect(status().isNotAcceptable());
		
	}
	
	//Get image
	@Test
	public void getAccountImage() throws Exception{
		Account account = new Account();
		account.setPassword("test");
		account.setNamefirst("test");
		account.setNamelast("test");
		account.setUsername("test");

		account.setEmail("test");
		account.setCity("test");
		account.setCountry("test");
		account.setAbout("test");
	    URL url = new URL("file:///C:/Users/Public/Pictures/Sample%20Pictures/Penguins.jpg");
	    account.setImage(url);
		File image = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg");
        when(service.getAccountImage("test")).thenReturn(
        		image);
		mockMvc.perform(
				get("/accounts/profile/test/image"))
				.andDo(print())
				.andExpect(jsonPath("$.image", is(account.getImage().toString())))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void getAccountImageFailure() throws Exception{
        when(service.getAccountImage("test")).thenThrow(
				new AccountImageNotFoundException());
		
        mockMvc.perform(
				get("/accounts/profile/test/image"))
				.andDo(print())
				.andExpect(status().isNotFound());
		
	}
	
	//Update account information
	@Test
	public void updateAccountInfo() throws Exception{
		Account account = new Account();
		account.setPassword("test");
		account.setNamefirst("test");
		account.setNamelast("test");
		account.setUsername("test");
		account.setEmail("test");
		account.setCity("test");
		account.setCountry("test");
		account.setAbout("test");
        when(service.updateAccountInfo(any(Account.class))).thenReturn(
				account);
		mockMvc.perform(
				post("/accounts/profile/test/updateinfo")
				.content(
				"{\"namefirst\":\"test\",\"namelast\":\"test\",\"city\":\"test\",\"country\":\"test\",\"about\":\"test\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())	
				.andDo(print());
		
	}
	
	@Test
	public void updateAccountInfoFailure() throws Exception{

        when(service.updateAccountInfo(any(Account.class))).thenThrow(
				new AccountUpdateFailureException());
		mockMvc.perform(
				post("/accounts/profile/test/updateinfo")
				.content(
				"{\"namefirst\":\"test\",\"namelast\":\"test\",\"city\":\"test\",\"country\":\"test\",\"about\":\"test\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())				
				.andDo(print());
		
	}
	
	//Add account institutions
	@Test
	public void addInstitutions() throws Exception{
		Institutions institution = new Institutions();
		institution.setId(1L);
		institution.setName("test");
		institution.setType("test");
		Date testStartdate = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		institution.setStart(testStartdate);
		Date testEnddate = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
				DateTimeZone.forID("UTC")).getMillis());
		institution.setEnd(testEnddate);
		institution.setDescription("test");
        when(service.addInstitution(any(Institutions.class),eq("test"))).thenReturn(
        		institution);
		mockMvc.perform(
				post("/accounts/profile/test/addinestitution")
				.content(
				"{\"name\":\"test\",\"type\":\"test\",\"start\":\"2007-9-23\",\"end\":\"2007-9-23\",\"description\":\"test\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted())	
				.andDo(print());
		
	}
	
	@Test
	public void addInstitutiosFailure() throws Exception{

		when(service.addInstitution(any(Institutions.class),eq("test"))).thenThrow(
				new AccountAndInstitotionsUpdateFailureException());
		mockMvc.perform(
				post("/accounts/profile/test/addinestitution")
				.content(
						"{\"name\":\"test\",\"type\":\"test\",\"start\":\"2007-9-23\",\"end\":\"2007-9-23\",\"description\":\"test\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())				
				.andDo(print());
		
	}
	
	//Delete account institutions
		@Test
		public void deleteInstitutions() throws Exception{
			
	        when(service.deleteInstitution(any(Institutions.class),eq("test"))).thenReturn(
	        		"success");
			mockMvc.perform(
					delete("/accounts/profile/test/deleteinestitution")
					.content(
					"{\"name\":\"test\",\"type\":\"test\",\"start\":\"2007-9-23\",\"end\":\"2007-9-23\",\"description\":\"test\"}")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())	
					.andDo(print());
			
		}
		
		@Test
		public void deleteInstitutiosFailure() throws Exception{

			when(service.deleteInstitution(any(Institutions.class),eq("test"))).thenReturn(
	        		null);
			mockMvc.perform(
					delete("/accounts/profile/test/deleteinestitution")
					.content(
							"{\"name\":\"test\",\"type\":\"test\",\"start\":\"2007-9-23\",\"end\":\"2007-9-23\",\"description\":\"test\"}")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())				
					.andDo(print());
			
		}
		
		//Change Password
		@Test
		public void changeAccountPassword() throws Exception {
			Account changedAccount = new Account();
			changedAccount.setId(1L);
			changedAccount.setPassword("test2");
			changedAccount.setNamefirst("test");
			changedAccount.setNamelast("test");
			changedAccount.setUsername("test");
			changedAccount.setEmail("test");
			changedAccount.setGender("test");
			Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
					DateTimeZone.forID("UTC")).getMillis());
			changedAccount.setBirthday(testBirthday);
			changedAccount.setCity("test");
			changedAccount.setCountry("test");

			when(service.changeAccountPassword(any(Account.class),any(Account.class))).thenReturn(
					changedAccount);
			mockMvc.perform(
					post("/accounts/profile/test/changepassword")
							.content(
									"{\"password_old\":\"test1\",\"password_new\":\"test2\"}")
							.contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isAccepted());

		}

		@Test
		public void changeAccountPasswordFailure() throws Exception {

			when(service.changeAccountPassword(any(Account.class),any(Account.class))).thenThrow(
					new NotAcceptableException());
			mockMvc.perform(
					post("/accounts/profile/test/changepassword")
							.content(
									"{\"password_old\":\"test1\",\"password_new\":\"test2\"}")
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotAcceptable()).andDo(print());
		}

		//Bookmark a account
		@Test
		public void bookmarkAccount() throws Exception {
			List<Account> list = new ArrayList<Account>();
			Account accountA = new Account();
			accountA.setId(1L);
			accountA.setPassword("testA");
			accountA.setNamefirst("testA");
			accountA.setNamelast("testA");
			accountA.setUsername("testA");
			accountA.setEmail("testA");
			accountA.setGender("testA");
			Date testBirthday = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
					DateTimeZone.forID("UTC")).getMillis());
			accountA.setBirthday(testBirthday);
			accountA.setCity("testA");
			accountA.setCountry("testA");
			list.add(accountA);
			Account accountB = new Account();
			accountB.setId(2L);
			accountB.setPassword("testB");
			accountB.setNamefirst("testB");
			accountB.setNamelast("testB");
			accountB.setUsername("testB");
			accountB.setEmail("testB");
			accountB.setGender("testB");
			accountB.setBirthday(testBirthday);
			accountB.setCity("testB");
			accountB.setCountry("testB");
			list.add(accountB);
			AccountList accountList = new AccountList(list);
			when(service.bookmarkAccount("test","testA")).thenReturn(
					accountList);
			mockMvc.perform(
					get("/accounts/test/testA/bookmarkaccount"))
					.andDo(print())
					.andExpect(status().isAccepted());

		}

		@Test
		public void bookmarkAccountFailure() throws Exception {

			when(service.bookmarkAccount("test","testA")).thenThrow(
					new NotAcceptableException());
			mockMvc.perform(
					get("/accounts/test/testA/bookmarkaccount"))
					.andExpect(status().isNotFound()).andDo(print());
		}

		@SuppressWarnings("unchecked")
		@Test
		public void getAccountsInstitutions() throws Exception {

			List<Institutions> list = new ArrayList<Institutions>();
			Institutions institutionsA = new Institutions();
			institutionsA.setId(1L);
			institutionsA.setName("Title A");
			institutionsA.setType("test A");
			Date testStartdate = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
					DateTimeZone.forID("UTC")).getMillis());
			institutionsA.setStart(testStartdate);
			Date testEnddate = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
					DateTimeZone.forID("UTC")).getMillis());
			institutionsA.setEnd(testEnddate);
			institutionsA.setDescription("test A");
			list.add(institutionsA);
			Institutions institutionsB = new Institutions();
			institutionsB.setId(2L);
			institutionsB.setName("Title B");
			institutionsB.setType("test B");
			institutionsB.setStart(testStartdate);
			institutionsB.setEnd(testEnddate);
			institutionsB.setDescription("test B");
			list.add(institutionsB);
			InstitutionList insList = new InstitutionList(list);
			when(service.getInstitutions("test")).thenReturn(insList);
			mockMvc.perform(get("/accounts/profile/test/getinstitutions"))
					.andExpect(
							jsonPath(
									"$.institutions[*].name",
									hasItems(endsWith("Title A"),
											endsWith("Title B"))))
					.andExpect(status().isOk()).andDo(print());
		}

		@Test
		public void getAccountsInstitutionsFailure() throws Exception {

			when(service.getInstitutions("test")).thenReturn(null);
			mockMvc.perform(get("/accounts/profile/test/getinstitutions")).andDo(print())
					.andExpect(status().isNotFound());
		}
		
		@SuppressWarnings("unchecked")
		@Test
		public void searchAccountsInstitutions() throws Exception {

			List<Institutions> list = new ArrayList<Institutions>();
			Institutions institutionsA = new Institutions();
			institutionsA.setId(1L);
			institutionsA.setName("Title A");
			institutionsA.setType("test A");
			Date testStartdate = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
					DateTimeZone.forID("UTC")).getMillis());
			institutionsA.setStart(testStartdate);
			Date testEnddate = new Date(new DateTime(2007, 9, 23, 0, 0, 0, 0,
					DateTimeZone.forID("UTC")).getMillis());
			institutionsA.setEnd(testEnddate);
			institutionsA.setDescription("test A");
			list.add(institutionsA);
			Institutions institutionsB = new Institutions();
			institutionsB.setId(2L);
			institutionsB.setName("Title B");
			institutionsB.setType("test B");
			institutionsB.setStart(testStartdate);
			institutionsB.setEnd(testEnddate);
			institutionsB.setDescription("test B");
			list.add(institutionsB);
			InstitutionList insList = new InstitutionList(list);
			when(service.searchInstitutions("test")).thenReturn(insList);
			mockMvc.perform(get("/accounts/profile/searchinstitutions/test"))
					.andExpect(
							jsonPath(
									"$.institutions[*].name",
									hasItems(endsWith("Title A"),
											endsWith("Title B"))))
					.andExpect(status().isOk()).andDo(print());
		}

		@Test
		public void searchAccountsInstitutionsFailure() throws Exception {

			when(service.searchInstitutions("test")).thenReturn(null);
			mockMvc.perform(get("/accounts/profile/getinstitutions/test")).andDo(print())
					.andExpect(status().isNotFound());
		}
}
