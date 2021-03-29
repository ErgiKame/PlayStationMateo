package model;

public class Inventari {

	private Artikujt artikull_id;
	private String gjendja;
	
	public Inventari() {}
	
	public Inventari(Artikujt artikull_id, String gjendja) {
		this.artikull_id = artikull_id;
		this.gjendja = gjendja;
	}

	public Artikujt getArtikull_id() {
		return artikull_id;
	}

	public void setArtikull_id(Artikujt artikull_id) {
		this.artikull_id = artikull_id;
	}

	public String getGjendja() {
		return gjendja;
	}

	public void setGjendja(String gjendja) {
		this.gjendja = gjendja;
	}
	
	
}
