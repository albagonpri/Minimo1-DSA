package edu.upc.dsa;

import edu.upc.dsa.exceptions.LectorNotFoundException;
import edu.upc.dsa.exceptions.LlibreNotFoundException;
import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Prestec;

import java.util.List;

public interface Manager {
    Lector afegirLector(String idLector, String nom, String cognoms, String DNI, String data_neix, String lloc_neix, String address);
    Llibre emmagatzemarLlibre(String id, String ISBN, String titol, String editorial, int any_publicacio, int numedicio, String autor, String tematica) throws LlibreNotFoundException;
    Llibre catalogarLlibre() throws LlibreNotFoundException;
    Prestec prestarLlibre(String idPrestec, String idLector, String idLlibre, String data_inici, String data_fi) throws LectorNotFoundException, LlibreNotFoundException;
    List<Prestec> consultarPrestecs(String idLector) throws LectorNotFoundException, LlibreNotFoundException;;
    void clear();
    int size();
}

