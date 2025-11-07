package edu.upc.dsa.models;

public class Lector {
    String idLector;
    String nom;
    String cognoms;
    String DNI;
    String data_neix;
    String address;

    public Lector() {}

    public Lector(String idLector, String nom, String cognoms, String DNI, String data_neix, String address) {
        this.idLector = idLector;
        this.nom = nom;
        this.cognoms = cognoms;
        this.DNI = DNI;
        this.data_neix = data_neix;
        this.address = address;
    }

    public String getIdLector() {return this.idLector;}
    public void setIdLector(String idLector) {this.idLector = idLector;}
    public String getNom() {return this.nom;}
    public void setNom(String nom) {this.nom = nom;}
    public String getCognoms() {return this.cognoms;}
    public void setCognoms(String cognoms) {this.cognoms = cognoms;}
    public String getDNI() {return this.DNI;}
    public void setDNI(String DNI) {this.DNI = DNI;}
    public String getData_neix() {return this.data_neix;}
    public void setData_neix(String data_neix) {this.data_neix = data_neix;}
    public String getAddress() {return this.address;}
    public void setAddress(String address) {this.address = address;}

    @Override
    public String toString() {
        return "Lector [id=" + idLector + ", Nom=" + nom + ", Cognoms=" + cognoms +
                ", DNI=" + DNI + ", data_neix=" + data_neix + ", address=" + address + "]";
    }
}
