package web.rest.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import web.rest.resources.AccountListResource;
import web.rest.resources.ChangePasswordResource;
import web.rest.resources.InstitutionsResource;
import web.rest.resources.RoomListResource;
import web.rest.resources.asm.AccountListResourceAsm;
import web.rest.resources.asm.InstitutionsResourceAsm;
import web.rest.resources.asm.RoomListResourceAsm;
import web.core.models.entities.Institutions;
import web.core.models.entities.Room;
import web.core.services.exceptions.AccountDoesNotExistException;
import web.core.services.exceptions.AccountImageNotFoundException;
import web.core.services.exceptions.AccountUpdateFailureException;
import web.core.services.exceptions.RoomExistsException;
import web.rest.resources.RoomResource;
import web.rest.resources.asm.RoomResourceAsm;
import web.core.models.entities.Account;
import web.core.services.AccountService;
import web.core.services.exceptions.AccountExistsException;
import web.core.services.exceptions.AccountNotMatchingException;
import web.core.services.util.AccountList;
import web.core.services.util.RoomList;
import web.rest.exceptions.ConflictException;
import web.rest.exceptions.NotAcceptableException;
import web.rest.exceptions.NotFoundException;
import web.core.services.exceptions.RoomDoesNotExistException;
import web.rest.resources.AccountResource;
import web.rest.resources.asm.AccountResourceAsm;

@RequestMapping("/accounts")
public class AccountController {

	private AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	//SignUp Controller.Will take information from user and create a new account
	@RequestMapping(value = "/signup" ,method = RequestMethod.POST)
	public ResponseEntity<AccountResource> createAccount(
			@RequestBody AccountResource sentAccount) {
		try {
			Account createdAccount = accountService.createAccount(sentAccount
					.toAccount());
			AccountResource res = new AccountResourceAsm()
					.toResource(createdAccount);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(res.getLink("self").getHref()));
			return new ResponseEntity<AccountResource>(res, headers,
					HttpStatus.CREATED);
		} catch (AccountExistsException exception) {
			throw new ConflictException(exception);
		}
	}
	
	//Get Account with Username Controller.Will get the account username and will return the account object 
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public ResponseEntity<AccountResource> getAccount(
			@PathVariable String username) {
		Account account = accountService.findAccount(username);
		if (account != null) {
			AccountResource res = new AccountResourceAsm().toResource(account);
			return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
		} else {
			return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Will verify account using Username and password or email or password 
	@RequestMapping(value = "/login" ,method = RequestMethod.POST)
	public ResponseEntity<AccountResource> verifyAccount(
			@RequestBody AccountResource sentAccount) {
		try {
			Account verifyAccount = accountService.verifyAccount(sentAccount
					.toAccount());
			AccountResource res = new AccountResourceAsm()
					.toResource(verifyAccount);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(res.getLink("self").getHref()));
			return new ResponseEntity<AccountResource>(res, headers,
					HttpStatus.OK);
		} catch (AccountNotMatchingException exception) {
			throw new NotAcceptableException(exception);
		}
	}
	
	//Will create a room for a user using his username
	@RequestMapping(value = "/{username}/createroom", method = RequestMethod.POST)
	public ResponseEntity<RoomResource> createRoom(
			@PathVariable String username, @RequestBody RoomResource res) {
		try {
			Room createdRoom = accountService.createRoom(username,
					res.toRoom());
			RoomResource createdRoomRes = new RoomResourceAsm()
					.toResource(createdRoom);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdRoomRes.getLink("self")
					.getHref()));
			return new ResponseEntity<RoomResource>(createdRoomRes, headers,
					HttpStatus.CREATED);
		}  catch (RoomExistsException exception) {
			throw new ConflictException(exception);
		}
	}
	
	//Search a room using roomname
	@RequestMapping(value = "/search/rooms/{room}", method = RequestMethod.GET)
	public ResponseEntity<RoomListResource> findRoom(
			@PathVariable("room") String room) {
		try {
			RoomList roomList = accountService.findRoom(room);
			RoomListResource roomListRes = new RoomListResourceAsm()
					.toResource(roomList);
			return new ResponseEntity<RoomListResource>(roomListRes,
					HttpStatus.OK);
		} catch (RoomDoesNotExistException exception) {
			throw new NotFoundException(exception);
		}
	}
	
	//Seach accounts uning account name
	@RequestMapping(value = "/search/accounts/{account}", method = RequestMethod.GET)
	public ResponseEntity<AccountListResource> findAccount(
			@PathVariable("account") String account) {
		try {
			AccountList accountList = accountService.findAccounts(account);
			AccountListResource accountListRes = new AccountListResourceAsm()
					.toResource(accountList);
			return new ResponseEntity<AccountListResource>(accountListRes,
					HttpStatus.OK);
		} catch (AccountDoesNotExistException exception) {
			throw new NotFoundException(exception);
		}
	}
	
	//Give the rooms user bookmarked
	@RequestMapping(value = "/{username}/bookmarkedrooms", method = RequestMethod.GET)
	public ResponseEntity<RoomListResource> findBookmarkedRooms(
			@PathVariable String username) {
		try {
			RoomList roomList = accountService.findBookmarkedRooms(username);
			RoomListResource roomListRes = new RoomListResourceAsm()
					.toResource(roomList);
			return new ResponseEntity<RoomListResource>(roomListRes,
					HttpStatus.OK);
		} catch (RoomDoesNotExistException exception) {
			throw new NotFoundException(exception);
		}
	}
	
	//Given the name of rooms user is admin
	@RequestMapping(value = "/{username}/ownedrooms", method = RequestMethod.GET)
	public ResponseEntity<RoomListResource> findOwnedRooms(
			@PathVariable String username) {
		try {
			RoomList roomList = accountService.findOwnedRooms(username);
			RoomListResource roomListRes = new RoomListResourceAsm()
					.toResource(roomList);
			return new ResponseEntity<RoomListResource>(roomListRes,
					HttpStatus.OK);
		} catch (RoomDoesNotExistException exception) {
			throw new NotFoundException(exception);
		}
	}
	
	//Name of the accounts user followed
	@RequestMapping(value = "/{username}/bookmarkedaccounts", method = RequestMethod.GET)
	public ResponseEntity<AccountListResource> getBokmarkedAccounts(
			@PathVariable String username) {
		try {
			AccountList accountList = accountService.getBookmarkedAccounts(username);
			AccountListResource accountListRes = new AccountListResourceAsm()
					.toResource(accountList);
			return new ResponseEntity<AccountListResource>(accountListRes,
					HttpStatus.OK);
		} catch (AccountDoesNotExistException exception) {
			throw new NotFoundException(exception);
		}
	}
	
	//Update Image of user
	@RequestMapping(value = "/profile/{username}/updateimage", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<AccountResource> updateAccountImage(@PathVariable("username") String username,
		        @RequestParam("image") final MultipartFile file)throws IOException {
		try {
			Account account = accountService.updateAccountImage(file,username);
		    AccountResource res = new AccountResourceAsm().toResource(account);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(res.getImage().toString()));
		    headers.setContentType(MediaType.TEXT_PLAIN);
			return new ResponseEntity<AccountResource>(res,headers, HttpStatus.OK);
		} catch(AccountUpdateFailureException exception) {
			throw new NotAcceptableException(exception);
		}
	}
	
	//Get Image of the user
	@RequestMapping(value = "/profile/{username}/image", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<AccountResource> getAccountImage(@PathVariable("username") String username)throws IOException {
		try {			
		    Account account = accountService.getAccountImage(username);
			AccountResource res = new AccountResourceAsm().toResource(account);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(res.getImage().toString()));
			return new ResponseEntity<AccountResource>(res,headers, HttpStatus.OK);
		} catch(AccountImageNotFoundException exception) {
			throw new NotFoundException(exception);
		}
	}
	
	//Upadate Account name,city,country,about
	@RequestMapping(value = "/profile/{username}/updateinfo", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<AccountResource> updateAccountInfo(@RequestBody AccountResource resource,
			@PathVariable("username") String username)throws IOException {
		try {
			resource.setUsername(username);
		    Account account = accountService.updateAccountInfo(resource.toAccount());
			AccountResource res = new AccountResourceAsm().toResource(account);
			return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
		} catch(AccountImageNotFoundException exception) {
			throw new NotAcceptableException(exception);
		}
	}
	
	//Add Institution for a account
	@RequestMapping(value = "/profile/{username}/addinestitution", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<InstitutionsResource> addInstitution(@RequestBody InstitutionsResource resource,
			@PathVariable("username") String username)throws IOException {
		try {
		    Institutions institution = accountService.addInstitution(resource.toInstitutions(), username);
			InstitutionsResource res = new InstitutionsResourceAsm().toResource(institution);
			return new ResponseEntity<InstitutionsResource>(res, HttpStatus.ACCEPTED);
		} catch(AccountImageNotFoundException exception) {
			throw new NotAcceptableException(exception);
		}
	}
	
	//Delete Institution for a account
	@RequestMapping(value = "/profile/{username}/deleteinestitution", method = RequestMethod.DELETE)
	public @ResponseBody
	ResponseEntity<InstitutionsResource> deleteInstitution(@RequestBody InstitutionsResource resource,
			@PathVariable("username") String username)throws IOException {
		    String status = accountService.deleteInstitution(resource.toInstitutions(), username);

		    if(status!=null){
		    return new ResponseEntity<InstitutionsResource>(HttpStatus.OK);
		    }
		    else{
			    return new ResponseEntity<InstitutionsResource>(HttpStatus.NOT_FOUND);

		    }
	}
	
	//Change Account Password
	@RequestMapping(value = "/profile/{username}/changepassword", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<ChangePasswordResource> changeAccountPassword(@RequestBody ChangePasswordResource resource,
			@PathVariable("username") String username)throws IOException {

		resource.setUsername(username);
		    Account account = accountService.changeAccountPassword(resource.toAccount_old(), resource.toAccount_new());

		    if(account!=null){
		    return new ResponseEntity<ChangePasswordResource>(HttpStatus.ACCEPTED);
		    }
		    else{
			    return new ResponseEntity<ChangePasswordResource>(HttpStatus.NOT_ACCEPTABLE);

		    }
	}
	
	//Change Account Password
	@RequestMapping(value = "/{username_own}/{username_follow}/bookmarkaccount", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<AccountListResource> bookmarkAccount( @PathVariable("username_own") String username_own ,
			@PathVariable("username_follow") String username_follow)throws IOException {
			try {
			    AccountList accountList = accountService.bookmarkAccount(username_own,username_follow);
				AccountListResource accountListRes = new AccountListResourceAsm()
						.toResource(accountList);
				return new ResponseEntity<AccountListResource>(accountListRes,
						HttpStatus.ACCEPTED);
			} catch (AccountDoesNotExistException exception) {
				throw new NotFoundException(exception);
			}
	}
}
