package web.rest.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import web.core.models.entities.Room;
import web.core.services.RoomService;
import web.core.services.exceptions.RoomNotExistException;
import web.core.services.exceptions.UnothorizedException;
import web.core.services.util.AccountList;
import web.rest.exceptions.ConflictException;
import web.rest.resources.AccountListResource;
import web.rest.resources.AccountResource;


import web.rest.resources.RoomResource;
import web.rest.resources.asm.AccountListResourceAsm;

import web.rest.resources.asm.RoomResourceAsm;

@RequestMapping("/room")
public class RoomController {

	private RoomService roomService;

	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}

	// SignUp Controller.Will take information from user and create a new account
	@RequestMapping(value = "/{roomname}", method = RequestMethod.GET)
	public ResponseEntity<RoomResource> getRoom(
			@PathVariable("roomname") String roomname) {
		try {
			Room room = roomService.getRoom(roomname);
			RoomResource resource = new RoomResourceAsm().toResource(room);
			return new ResponseEntity<RoomResource>(resource, HttpStatus.OK);
		} catch (RoomNotExistException ex) {
			throw new ConflictException();
		}
	}
	
	// Delete a room
	@RequestMapping(value = "/{roomname}/deleteroom", method = RequestMethod.DELETE)
	public ResponseEntity<RoomResource> deleteRoom(
			@PathVariable("roomname") String roomname) {
		try {
			 roomService.deleteRoom(roomname);
				
			return new ResponseEntity<RoomResource>(HttpStatus.OK);
		
		} catch (UnothorizedException ex) {
			throw new ConflictException();
		}
	}
	
// Add admin to a room
	@RequestMapping(value = "/{roomname}/addadmin", method = RequestMethod.POST)
	public ResponseEntity<AccountListResource> addAdmin(@PathVariable String roomname,
			@RequestBody AccountResource res) {
		try {
			AccountList account = roomService.addAdmin(roomname,res.toAccount());
			AccountListResource resource=new AccountListResourceAsm().toResource(account);
			return new ResponseEntity<AccountListResource>(resource,HttpStatus.OK);
		
		} catch (UnothorizedException ex) {
			throw new ConflictException();
		}
	}
	
	// Remove admin to a room
		@RequestMapping(value = "/{roomname}/removeadmin", method = RequestMethod.DELETE)
		public ResponseEntity<AccountListResource> removeAdmin(@PathVariable String roomname,
				@RequestBody AccountResource res) {
			try {
				AccountList account = roomService.removeAdmin(roomname,res.toAccount());
				AccountListResource resource=new AccountListResourceAsm().toResource(account);
				return new ResponseEntity<AccountListResource>(resource,HttpStatus.OK);
			
			} catch (UnothorizedException ex) {
				throw new ConflictException();
			}
		}
		
		//Add Member to the room
		@RequestMapping(value = "/{roomname}/addmember", method = RequestMethod.POST)
		public ResponseEntity<AccountListResource> addMember(@PathVariable String roomname,
				@RequestBody AccountResource res) {
			try {
				AccountList account = roomService.addMember(roomname,res.toAccount());
				AccountListResource resource=new AccountListResourceAsm().toResource(account);
				return new ResponseEntity<AccountListResource>(resource,HttpStatus.OK);
			
			} catch (UnothorizedException ex) {
				throw new ConflictException();
			}
		}
		
		//Remove Member to the room
		@RequestMapping(value = "/{roomname}/removemember", method = RequestMethod.DELETE)
		public ResponseEntity<AccountListResource> removeMember(@PathVariable String roomname,
				@RequestBody AccountResource res) {
			try {
				AccountList account = roomService.removeMember(roomname,res.toAccount());
				AccountListResource resource=new AccountListResourceAsm().toResource(account);
				return new ResponseEntity<AccountListResource>(resource,HttpStatus.OK);
			
			} catch (UnothorizedException ex) {
				throw new ConflictException();
			}
		}
}
