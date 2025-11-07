package edu.upc.dsa.models;

public class Llibre {
    String id;
    String ISBN;
    String titol;
    String editorial;
    int any_publicacio;
    int numedicio;
    String autor;
    String tematica;
    boolean emmagatzemat = false;

    public Llibre() {
    }

    public Llibre(String id, String ISBN, String titol, String editorial, int any_publicacio, int numedicio, String autor, String tematica) {
        this.id = id;
        this.ISBN = ISBN;
        this.titol = titol;
        this.editorial = editorial;
        this.any_publicacio = any_publicacio;
        this.numedicio = numedicio;
        this.autor = autor;
        this.tematica = tematica;
        this.emmagatzemat = false;
    }

    public String getId() {return this.id;}
    public void setId(String id) {this.id = id;}
    public String getISBN() {return this.ISBN;}
    public void setISBN(String ISBN) {this.ISBN = ISBN;}
    public String getTitol() {return this.titol;}
    public void setTitol(String titol) {this.titol = titol;}
    public String getEditorial() {return this.editorial;}
    public void setEditorial(String editorial) {this.editorial = editorial;}
    public int getAny_publicacio() {return this.any_publicacio;}
    public void setAny_publicacio(int any_publicacio) {this.any_publicacio = any_publicacio;}
    public int getNumedicio() {return this.numedicio;}
    public void setNumedicio(int numedicio) {this.numedicio = numedicio;}
    public String getAutor() {return this.autor;}
    public void setAutor(String autor) {this.autor = autor;}
    public String getTematica() {return this.tematica;}
    public void setTematica(String tematica) {this.tematica = tematica;}
    public boolean isEmmagatzemat() {return emmagatzemat;}
    public void setEmmagatzemat(boolean emmagatzemat) {this.emmagatzemat = emmagatzemat;}

    @Override
    public String toString() {
        return "Llibre [id=" + id + ", ISBN=" + ISBN + ", titol=" + titol + ", editorial=" + editorial +
                ", any_publicacio=" + any_publicacio + ", num_edicio=" + numedicio +
                ", autor=" + autor + ", tematica=" + tematica + "]";
    }
}