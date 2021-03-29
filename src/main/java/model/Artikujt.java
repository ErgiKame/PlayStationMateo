package model;

import java.time.LocalDate;

public class Artikujt {
    private int artikull_id;
    private String emri_artikullit;
    private String createdDate;

    public Artikujt(int artikull_id, String emri_artikullit) {
        this.artikull_id = artikull_id;
        this.emri_artikullit = emri_artikullit;
    }

    public Artikujt() {

    }

    public int getArtikull_id() {
        return artikull_id;
    }

    public void setArtikull_id(int artikull_id) {
        this.artikull_id = artikull_id;
    }

    public String getEmri_artikullit() {
        return emri_artikullit;
    }

    public void setEmri_artikullit(String emri_artikullit) {
        this.emri_artikullit = emri_artikullit;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
