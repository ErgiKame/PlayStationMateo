package model;

import java.sql.Date;

public class Furnizim {

	private int id;
	private Artikujt artikull_id;
	private double sasia, cmimi, vlera;
	private String created_date;
	
	public Furnizim() {}
	
	public Furnizim(int id, Artikujt artikull_id, double sasia, double cmimi, String created_date) {
		this.id = id;
		this.artikull_id = artikull_id;
		this.sasia = sasia;
		this.cmimi = cmimi;
		this.created_date = created_date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public double getVlera() {
		return vlera;
	}

	public void setVlera(double vlera) {
		this.vlera = vlera;
	}

	public Artikujt getArtikull_id() {
		return artikull_id;
	}

	public void setArtikull_id(Artikujt artikull_id) {
		this.artikull_id = artikull_id;
	}
}
