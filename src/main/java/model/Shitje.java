package model;

import java.sql.Date;

public class Shitje {

	private int id;
	private Perdoruesit userid;
	private Artikujt artikull_id;
	private String created_date;
	private double sasia, cmimi, vlera;
	private int staff;
	
	public Shitje() {}

	public Shitje(int id, Artikujt artikull_id, String created_date, double sasia,
				  double cmimi, double vlera, int staff, Perdoruesit userid) {
		super();
		this.id = id;
		this.artikull_id = artikull_id;
		this.created_date = created_date;
		this.sasia = sasia;
		this.cmimi = cmimi;
		this.vlera = vlera;
		this.staff = staff;
		this.userid = userid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Artikujt getArtikull_id() {
		return artikull_id;
	}

	public void setArtikull_id(Artikujt artikull_id) {
		this.artikull_id = artikull_id;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public double getSasia() {
		return sasia;
	}

	public void setSasia(double sasia) {
		this.sasia = sasia;
	}

	public double getCmimi() {
		return cmimi;
	}

	public void setCmimi(double cmimi) {
		this.cmimi = cmimi;
	}

	public double getVlera() {
		return vlera;
	}

	public void setVlera(double vlera) {
		this.vlera = vlera;
	}

	public int getStaff() {
		return staff;
	}

	public void setStaff(int staff) {
		this.staff = staff;
	}

	public Perdoruesit getUserid() { return userid; }

	public void setUserid(Perdoruesit userid) { this.userid = userid; }
}