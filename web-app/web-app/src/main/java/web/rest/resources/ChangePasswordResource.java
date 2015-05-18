package web.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import web.core.models.entities.Account;

public class ChangePasswordResource extends ResourceSupport {

	private String username;
	private String password_old;
	private String password_new;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword_old() {
		return password_old;
	}

	public void setPassword_old(String password_old) {
		this.password_old = password_old;
	}

	public String getPassword_new() {
		return password_new;
	}

	public void setPassword_new(String password_new) {
		this.password_new = password_new;
	}

	public Account toAccount_old() {
		Account account = new Account();

		account.setPassword(password_old);
		account.setUsername(username);

		return account;
	}

	public Account toAccount_new() {
		Account account = new Account();

		account.setPassword(password_new);
		account.setUsername(username);

		return account;
	}
}
