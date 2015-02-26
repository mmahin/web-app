package web.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import web.core.models.entities.Account;
import web.rest.mvc.AccountController;
import web.rest.resources.AccountResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by Chris on 6/28/14.
 */
public class AccountResourceAsm extends
		ResourceAssemblerSupport<Account, AccountResource> {
	public AccountResourceAsm() {
		super(AccountController.class, AccountResource.class);
	}

	@Override
	public AccountResource toResource(Account account) {
		AccountResource res = new AccountResource();
		res.setPassword(account.getPassword());
		res.setNamefirst(account.getNamefirst());
		res.setNamelast(account.getNamelast());
		res.setUsername(account.getUsername());
		res.setEmail(account.getEmail());
		res.setGender(account.getGender());
		res.setBirthday(account.getBirthday());
		res.setCity(account.getCity());
		res.setCountry(account.getCountry());
		res.setAbout(account.getAbout());
		res.setImage(account.getImage());
		res.setOnline(account.isOnline());
		res.setBookmarkedaccount(account.getBookmarkedaccount());
		res.setBookmarkedroom(account.getBookmarkedroom());
		res.setOwnedroom(account.getOwnedroom());
		res.add(linkTo(
				methodOn(AccountController.class).getAccount(
						account.getUsername())).withSelfRel());

		return res;
	}
}