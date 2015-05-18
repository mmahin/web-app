package web.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import web.rest.mvc.AccountController;
import web.rest.resources.AccountResource;
import web.rest.resources.asm.AccountResourceAsm;
import web.core.services.util.AccountList;
import web.rest.resources.AccountListResource;

public class AccountListResourceAsm extends
		ResourceAssemblerSupport<AccountList, AccountListResource> {
	public AccountListResourceAsm() {
		super(AccountController.class, AccountListResource.class);
	}

	@Override
	public AccountListResource toResource(AccountList accountList) {
		List<AccountResource> resList = new AccountResourceAsm()
				.toResources(accountList.getAccounts());
		AccountListResource finalRes = new AccountListResource();
		finalRes.setAccounts(resList);
		return finalRes;
	}
}
