package edu.upc.dsa.models;

public class Prestec {
    String idPrestec;
    String idLector;
    String idLlibre;
    String data_inici;
    String data_fi;
    Boolean enTramit;

    public Prestec(String idPrestec, String idLlibre, String idLector, String ISBN, String data_inici, String data_fi) {}

    public Prestec(String idPrestec, String idLector, String idLlibre, String data_inici, String data_fi) {
        this.idPrestec = idPrestec;
        this.idLector = idLector;
        this.idLlibre = idLlibre;
        this.data_inici = data_inici;
        this.data_fi = data_fi;
        this.enTramit = true;
    }

    public String getIdPrestec() {return this.idPrestec;}
    public void setIdPrestec(String idPrestec) {this.idPrestec = idPrestec;}
    public String getIdLector() {return this.idLector;}
    public void setIdLector(String idLector) {this.idLector = idLector;}
    public String getIdLlibre() {return this.idLlibre;}
    public void setIdLlibre(String idLlibre) {this.idLlibre = idLlibre;}
    public String getData_inici() {return this.data_inici;}
    public void setData_inici(String data_inici) {this.data_inici = data_inici;}
    public String getData_fi() {return this.data_fi;}
    public void setData_fi(String data_fi) {this.data_fi = data_fi;}
    public Boolean getEnTramit() {return this.enTramit;}
    public void setEnTramit(Boolean enTramit) {this.enTramit = enTramit;}

    @Override
    public String toString() {
        return "Prestec [idPrestec=" + idPrestec + ", idLector=" + idLector + ", idLlibre=" + idLlibre +
                ", data_inici=" + data_inici + ", data_fi=" + data_fi + ", enTramit=" + enTramit + "]";
    }
}
