package edu.upc.dsa;

import edu.upc.dsa.exceptions.LectorNotFoundException;
import edu.upc.dsa.exceptions.LlibreNotFoundException;
import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Prestec;

import java.util.List;

    public interface Manager {
       public Lector afegirLector(String idLector, String nom, String cognoms, String DNI, String data_neix, String address);
       public Llibre emmagatzemarLlibre(String id, String ISBN, String titol, String editorial, int any_publicacio, int numedicio, String autor, String tematica) throws LlibreNotFoundException;
       public Llibre catalogarLlibre() throws LlibreNotFoundException;
       public Prestec prestarLlibre(String idPrestec, String idLlibre, String idLector, String data_inici, String data_fi);
       public List<Prestec> consultarPrestecs(String idPrestec,String idLector, String ISBN, String data_inici, String data_fi) throws LectorNotFoundException,LlibreNotFoundException;
       public void clear();
       public int size();
       List<Prestec> consultarPrestecs(String idLector) throws LectorNotFoundException, LlibreNotFoundException;
}
