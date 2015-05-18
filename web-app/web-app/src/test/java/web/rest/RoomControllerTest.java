package web.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import web.core.models.entities.Account;
import web.core.models.entities.Room;
import web.core.services.RoomService;
import web.core.services.exceptions.AccountExistsException;
import web.core.services.exceptions.RoomNotExistException;
import web.core.services.exceptions.UnothorizedException;
import web.core.services.util.AccountList;
import web.rest.mvc.RoomController;

public class RoomControllerTest {

	@InjectMocks
	private RoomController controller;
	@Mock
	private RoomService service;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	// Get room using room name
	@Test
	public void getRoomUsingRoomName() throws Exception {
		Room room = new Room();
		room.setRoomid(1L);
		room.setRoomname("test");
		room.setDescription("test");
		when(service.getRoom("test")).thenReturn(room);

		mockMvc.perform(get("/room/test")).andDo(print())
				.andExpect(jsonPath("$.roomname", is(room.getRoomname())))
				.andExpect(status().isOk());

	}

	@Test
	public void getRoomUsingRoomNameFailure() throws Exception {

		when(service.getRoom("test")).thenThrow(new RoomNotExistException());

		mockMvc.perform(get("/room/test")).andDo(print())
				.andExpect(status().isConflict());
	}

	// Delete Room
	@Test
	public void deleteRoom() throws Exception {
		Room room = new Room();
		room.setRoomid(1L);
		room.setRoomname("test");
		room.setDescription("test");
		when(service.deleteRoom("test")).thenReturn("Success");

		mockMvc.perform(delete("/room/test/deleteroom")).andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void deleteRoomFailure() throws Exception {

		when(service.deleteRoom("test")).thenThrow(new UnothorizedException());

		mockMvc.perform(delete("/room/test/deleteroom")).andDo(print())
				.andExpect(status().isConflict());
	}

	// Add admin to the room
	@Test
	public void addAdminToRoom() throws Exception {
		List<Account> list = new ArrayList<Account>();
		Account accountA = new Account();
		accountA.setId(1L);
		accountA.setUsername("Title A");
		list.add(accountA);
		Account accountB = new Account();
		accountB.setId(1L);
		accountB.setUsername("Title B");
		list.add(accountB);
		AccountList accountList = new AccountList(list);
		when(service.addAdmin(eq("test"), any(Account.class))).thenReturn(
				accountList);

		mockMvc.perform(
				post("/room/test/addadmin").content(
						"{\"username\":\"Title B\"}").contentType(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void addAdminToRoomFailure() throws Exception {

		when(service.addAdmin(eq("test"), any(Account.class))).thenThrow(
				new UnothorizedException());

		mockMvc.perform(
				post("/room/test/addAdmin").content(
						"{\"username\":\"Title B\"}").contentType(
						MediaType.APPLICATION_JSON))

		.andDo(print()).andExpect(status().isNotFound());
	}

	// Remove admin to the room
	@Test
	public void removeAdminFromRoom() throws Exception {
		List<Account> list = new ArrayList<Account>();
		Account accountA = new Account();
		accountA.setId(1L);
		accountA.setUsername("Title A");
		list.add(accountA);
		Account accountB = new Account();
		accountB.setId(1L);
		accountB.setUsername("Title B");
		list.add(accountB);
		AccountList accountList = new AccountList(list);
		when(service.removeAdmin(eq("test"), any(Account.class))).thenReturn(
				accountList);

		mockMvc.perform(
				delete("/room/test/removeadmin").content(
						"{\"username\":\"Title C\"}").contentType(
						MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void removeAdminFromRoomFailure() throws Exception {

		when(service.removeAdmin(eq("test"), any(Account.class))).thenThrow(
				new UnothorizedException());

		mockMvc.perform(delete("/room/test/removeAdmin")).andDo(print())
				.andExpect(status().isNotFound());
	}

	// Add Member to the room
	@Test
	public void addMemeberToRoom() throws Exception {
		List<Account> list = new ArrayList<Account>();
		Account accountA = new Account();
		accountA.setId(1L);
		accountA.setUsername("Title A");
		list.add(accountA);
		Account accountB = new Account();
		accountB.setId(1L);
		accountB.setUsername("Title B");
		list.add(accountB);
		AccountList accountList = new AccountList(list);
		when(service.addMember(eq("test"), any(Account.class))).thenReturn(
				accountList);

		mockMvc.perform(
				post("/room/test/addmember").content(
						"{\"username\":\"Title C\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void addMemeberToRoomFailure() throws Exception {

		when(service.addMember(eq("test"), any(Account.class))).thenThrow(
				new AccountExistsException());

		mockMvc.perform(post("/room/test/addmember")).andDo(print())
				.andExpect(status().isNotFound());
	}
	// Remove Member to the room
	@Test
	public void removeMemeberFromRoom() throws Exception {
		List<Account> list = new ArrayList<Account>();
		Account accountA = new Account();
		accountA.setId(1L);
		accountA.setUsername("Title A");
		list.add(accountA);
		Account accountB = new Account();
		accountB.setId(1L);
		accountB.setUsername("Title B");
		list.add(accountB);
		AccountList accountList = new AccountList(list);
		when(service.removeMember(eq("test"), any(Account.class))).thenReturn(
				accountList);

		mockMvc.perform(
				delete("/room/test/removemember").content(
						"{\"username\":\"Title C\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void removeMemeberFromRoomFailure() throws Exception {

		when(service.removeMember(eq("test"), any(Account.class))).thenThrow(
				new AccountExistsException());

		mockMvc.perform(delete("/room/test/removemember")).andDo(print())
				.andExpect(status().isNotFound());
	}
}
