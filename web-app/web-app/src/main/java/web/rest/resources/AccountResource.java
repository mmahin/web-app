package web.rest.resources;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.ResourceSupport;

import web.core.models.entities.Account;
import web.core.models.entities.Room;

/**
 * Created by Chris on 6/28/14.
 */
public class AccountResource extends ResourceSupport {

	private String username;
	private String email;
	private String password;
	private String namefirst;
	private String namelast;
	private String gender;
	private Date birthday;
	private String city;
	private String country;
	private String about;
	private URL image;
	private boolean online;
	private Collection<Room> ownedroom = new ArrayList<Room>();
	private Collection<Room> bookmarkedroom = new ArrayList<Room>();
	private Collection<Account> bookmarkedaccount = new ArrayList<Account>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getNamefirst() {
		return namefirst;
	}

	public void setNamefirst(String namefirst) {
		this.namefirst = namefirst;
	}

	public String getNamelast() {
		return namelast;
	}

	public void setNamelast(String namelast) {
		this.namelast = namelast;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public URL getImage() {
		return image;
	}

	public void setImage(URL image) {
		this.image = image;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Collection<Room> getOwnedroom() {
		return ownedroom;
	}

	public void setOwnedroom(Collection<Room> ownedroom) {
		this.ownedroom = ownedroom;
	}

	public Collection<Room> getBookmarkedroom() {
		return bookmarkedroom;
	}

	public void setBookmarkedroom(Collection<Room> bookmarkedroom) {
		this.bookmarkedroom = bookmarkedroom;
	}

	public Collection<Account> getBookmarkedaccount() {
		return bookmarkedaccount;
	}

	public void setBookmarkedaccount(Collection<Account> bookmarkedaccount) {
		this.bookmarkedaccount = bookmarkedaccount;
	}

	public Account toAccount() {
		Account account = new Account();
		account.setPassword(password);
		account.setNamefirst(namefirst);
		account.setNamelast(namelast);
		account.setUsername(username);
		account.setEmail(email);
		account.setGender(gender);
		account.setBirthday(birthday);
		account.setCity(city);
		account.setCountry(country);
		account.setAbout(about);
		account.setImage(image);
		account.setOnline(online);
		account.setBookmarkedaccount(bookmarkedaccount);
		account.setBookmarkedroom(bookmarkedroom);
		account.setOwnedroom(ownedroom);
		return account;
	}

}
