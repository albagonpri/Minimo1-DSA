package edu.upc.dsa;

import edu.upc.dsa.exceptions.LectorNotFoundException;
import edu.upc.dsa.exceptions.LlibreNotFoundException;
import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Prestec;
import org.apache.log4j.Logger;

import java.util.*;

public class ManagerImpl implements Manager {
    private static Manager instance;
    protected List<Llibre> llibresCatalogats;
    protected List<Lector> lectors;
    protected List<Prestec> prestecs;
    protected LinkedList<LinkedList<Llibre>> munts;
    protected Map<String,Integer> stockByIsbn;
    protected Map<String,String> isbnByCatalogId;
    final static Logger logger = Logger.getLogger(ManagerImpl.class);

    private ManagerImpl() {
        this.llibresCatalogats = new LinkedList<>();
        this.lectors = new LinkedList<>();
        this.prestecs = new LinkedList<>();
        this.munts = new LinkedList<>();
        this.stockByIsbn = new HashMap<>();
        this.isbnByCatalogId = new HashMap<>();
    }

    public static Manager getInstance() {
        if (instance == null) instance = new ManagerImpl();
        return instance;
    }

    public int size() {
        logger.info("size IN");
        int ret = this.lectors.size();
        logger.info("size OUT " + ret);
        return ret;
    }

    public Lector afegirLector(String idLector, String nom, String cognoms, String DNI, String data_neix, String lloc_neix, String address) {
        logger.info("afegirLector IN " + idLector);
        for (Lector l : lectors) {
            if (l.getIdLector().equals(idLector)) {
                l.setNom(nom);
                l.setCognoms(cognoms);
                l.setDNI(DNI);
                l.setData_neix(data_neix);
                l.setLloc_neix(lloc_neix);
                l.setAddress(address);
                logger.info("afegirLector OUT " + l);
                return l;
            }
        }
        Lector l = new Lector(idLector, nom, cognoms, DNI, data_neix, lloc_neix, address);
        lectors.add(l);
        logger.info("afegirLector OUT " + l);
        return l;
    }

    public Llibre emmagatzemarLlibre(String id, String ISBN, String titol, String editorial, int any_publicacio, int numedicio, String autor, String tematica) throws LlibreNotFoundException {
        logger.info("emmagatzemarLlibre IN " + id);
        Llibre llibre = new Llibre(id, ISBN, titol, editorial, any_publicacio, numedicio, autor, tematica);
        if (munts.isEmpty() || munts.getLast().size() == 10) munts.addLast(new LinkedList<>());
        munts.getLast().addLast(llibre);
        logger.info("emmagatzemarLlibre OUT " + llibre.getId());
        return llibre;
    }

    public Llibre catalogarLlibre() throws LlibreNotFoundException {
        logger.info("catalogarLlibre IN");
        if (munts.isEmpty()) {
            logger.info("catalogarLlibre ERROR");
            throw new LlibreNotFoundException();
        }
        while (!munts.isEmpty() && munts.getFirst().isEmpty()) munts.removeFirst();
        if (munts.isEmpty()) {
            logger.info("catalogarLlibre ERROR");
            throw new LlibreNotFoundException();
        }
        Llibre llibre = munts.getFirst().removeLast();
        if (munts.getFirst().isEmpty()) munts.removeFirst();
        llibresCatalogats.add(llibre);
        isbnByCatalogId.put(llibre.getId(), llibre.getISBN());
        stockByIsbn.put(llibre.getISBN(), stockByIsbn.getOrDefault(llibre.getISBN(), 0) + 1);
        logger.info("catalogarLlibre OUT " + llibre.getId());
        return llibre;
    }

    public Prestec prestarLlibre(String idPrestec, String idLector, String idLlibre, String data_inici, String data_fi) throws LectorNotFoundException, LlibreNotFoundException {
        logger.info("prestarLlibre IN " + idPrestec);
        boolean lectorExisteix = false;
        for (Lector l : lectors) {
            if (l.getIdLector().equals(idLector)) {
                lectorExisteix = true;
                break;
            }
        }
        if (!lectorExisteix) {
            logger.info("prestarLlibre ERROR lector");
            throw new LectorNotFoundException();
        }
        String isbn = isbnByCatalogId.get(idLlibre);
        int stock = isbn == null ? 0 : stockByIsbn.getOrDefault(isbn, 0);
        if (stock <= 0) {
            logger.info("prestarLlibre ERROR stock");
            throw new LlibreNotFoundException();
        }
        stockByIsbn.put(isbn, stock - 1);
        Prestec p = new Prestec(
                idPrestec,
                idLector,
                idLlibre,
                data_inici,
                data_fi
        );
        prestecs.add(p);
        logger.info("prestarLlibre OUT " + p.getIdPrestec());
        return p;
    }

    public List<Prestec> consultarPrestecs(String idLector) throws LectorNotFoundException, LlibreNotFoundException {
        logger.info("consultarPrestecs IN " + idLector);
        List<Prestec> res = new LinkedList<>();
        boolean existeix = false;
        for (Prestec p : prestecs) {
            if (p.getIdLector() != null && p.getIdLector().equals(idLector)) {
                res.add(p);
                existeix = true;
            }
        }
        if (!existeix) {
            boolean lectorExisteix = false;
            for (Lector l : lectors) {
                if (l.getIdLector().equals(idLector)) {
                    lectorExisteix = true;
                    break;
                }
            }
            if (!lectorExisteix) {
                logger.info("consultarPrestecs ERROR lector");
                throw new LectorNotFoundException();
            }
        }
        logger.info("consultarPrestecs OUT " + res.size());
        return res;
    }

    public void clear() {
        logger.info("clear IN");
        llibresCatalogats.clear();
        lectors.clear();
        prestecs.clear();
        munts.clear();
        stockByIsbn.clear();
        isbnByCatalogId.clear();
        logger.info("clear OUT");
    }
}
