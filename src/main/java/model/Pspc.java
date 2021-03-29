package model;

public class Pspc {

    private int id;
    private String lloji, koment, created_date;
    private double xhiro;

    public Pspc(){

    }

    public Pspc(int id, String lloji, String koment, String created_date, double xhiro) {
        this.id = id;
        this.lloji = lloji;
        this.koment = koment;
        this.created_date = created_date;
        this.xhiro = xhiro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLloji() {
        return lloji;
    }

    public void setLloji(String lloji) {
        this.lloji = lloji;
    }

    public String getKoment() {
        return koment;
    }

    public void setKoment(String koment) {
        this.koment = koment;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public double getXhiro() {
        return xhiro;
    }

    public void setXhiro(double xhiro) {
        this.xhiro = xhiro;
    }
}
