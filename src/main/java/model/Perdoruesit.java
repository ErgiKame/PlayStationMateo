package model;


public class Perdoruesit {

	private int userid;
	private String username,password,
	name, access, telefon;
	
	public Perdoruesit() {
		
	}
	
	public Perdoruesit(String username, String password, String name,
			String access, String telefon) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.access = access;
		this.telefon = telefon;
		
		

	}
	
	

	



	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	


	
	
}
