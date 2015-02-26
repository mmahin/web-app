package web.core.models.entities;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;
	@Column(name="username",unique=true,nullable=false)
	private String username;
	@Column(name="email",unique=true,nullable=false)
	private String email;
	@Column(name="password",nullable=false)
	private String password;
	@Column(name="namefirst",nullable=false)
	private String namefirst;
	@Column(name="namelast",nullable=false)
	private String namelast;
	@Column(name="gender",nullable=false)
	private String gender;
	@Column(name="birthday",nullable=false)
	private Date birthday;
	@Column(name="city",nullable=false)
	private String city;
	@Column(name="country",nullable=false)
	private String country;
	private String about;
	private URL image;
	private boolean online;
	@ManyToMany(mappedBy="admins")
	private Collection<Room> ownedroom = new ArrayList<Room>();
	@OneToMany
	@JoinTable(name = "Bookmarked_rooms", joinColumns = @JoinColumn(name="Account_id"), inverseJoinColumns = @JoinColumn(name="Room_id"))
	private Collection<Room> bookmarkedroom = new ArrayList<Room>();
	@OneToMany
	@JoinTable(name = "Bookmarked_accounts", joinColumns = @JoinColumn(name="Follower_id"), inverseJoinColumns = @JoinColumn(name="Following_id"))
	private Collection<Account> bookmarkedaccount = new ArrayList<Account>();
	@ManyToMany(mappedBy="accounts")
	private Collection<Institutions> institutions = new ArrayList<Institutions>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getPassword() {
		return password;
	}

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



}
