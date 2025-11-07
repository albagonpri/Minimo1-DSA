package edu.upc.dsa;

import edu.upc.dsa.exceptions.LectorNotFoundException;
import edu.upc.dsa.exceptions.LlibreNotFoundException;
import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Prestec;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

public class ManagerImpl implements Manager {
    private static Manager instance;
    protected List<Llibre> llibres;
    protected List<Lector> lectors;
    protected List<Prestec> prestecs;
    protected LinkedList<LinkedList<Llibre>> munt;
    final static Logger logger = Logger.getLogger(ManagerImpl.class);

    private ManagerImpl() {
        this.llibres = new LinkedList<>();
        this.lectors = new LinkedList<>();
        this.prestecs = new LinkedList<>();
        this.munt = new LinkedList<>();
    }

    public static Manager getInstance() {
        if (instance == null) instance = new ManagerImpl();
        return instance;
    }

    @Override
    public int size() {
        int ret = this.lectors.size();
        logger.info("size " + ret);
        return ret;
    }

    @Override
    public Lector afegirLector(String idLector, String nom, String cognoms, String DNI, String data_neix, String address) {
        logger.info("Nou lector= " + idLector);
        Lector l = new Lector(idLector, nom, cognoms, DNI, data_neix, address);
        this.lectors.add(l);
        logger.info("Nou lector afegit");
        return l;
    }

    @Override
    public Llibre emmagatzemarLlibre(String id, String ISBN, String titol, String editorial, int any_publicacio, int numedicio, String autor, String tematica) throws LlibreNotFoundException {
        logger.info("Nou llibre=" + titol);
        Llibre llibre = new Llibre(id, ISBN, titol, editorial, any_publicacio, numedicio, autor, tematica);
        if (munt.isEmpty() || munt.getLast().size() >= 10) {
            munt.addLast(new LinkedList<>());
        }
        munt.getLast().addFirst(llibre);
        logger.info("Nou llibre emmagatzemat");
        return llibre;
    }
    @Override
    public Llibre catalogarLlibre() throws LlibreNotFoundException {
        logger.info("Catalogar llibre");
        if (this.munt.isEmpty()) {
            logger.warn("No hi ha llibres.");
            throw new LlibreNotFoundException();
        }
        if (this.munt.getFirst().isEmpty()) {
            this.munt.removeFirst();
        }
        if (this.munt.isEmpty()) {
            logger.warn("No hi ha més llibres.");
            throw new LlibreNotFoundException();
        }
        Llibre llibre = this.munt.getFirst().removeFirst();
        if (this.munt.getFirst().isEmpty()) {
            this.munt.removeFirst();
        }
        logger.info("S'ha catalogat el llibre.");
        return llibre;
    }
    @Override
    public Prestec prestarLlibre(String idPrestec, String idLector, String idLlibre, String data_inici, String data_fi) {
        logger.info("Nou prestec " + idPrestec);
        // Orden correcto: (idPrestec, idLector, idLlibre, data_inici, data_fi)
        Prestec p = new Prestec(idPrestec, idLector, idLlibre, data_inici, data_fi);
        this.prestecs.add(p);
        logger.info("Nou prestec afegit");
        return p;
    }
    @Override
    public List<Prestec> consultarPrestecs(String idPrestec, String idLector, String ISBN, String data_inici, String data_fi) throws LectorNotFoundException, LlibreNotFoundException {
        logger.info("Consultar prestecs del lector amb id=" + idLector);
        List<Prestec> prestecsLector = new LinkedList<>();
        for (Prestec p : this.prestecs) {
            if (p.getIdLector().equals(idLector)) {
                logger.info("consultarPrestecs(" + idLector + "): " + p);
                prestecsLector.add(p);
            }
        }
        if (prestecsLector.isEmpty()) {
            logger.warn("El lector amb id=" + idLector + " no te cap prestec.");
            throw new LectorNotFoundException();
        }
        return prestecsLector;
    }

    @Override
    public List<Prestec> consultarPrestecs(String idLector) throws LectorNotFoundException {
        logger.info("Consultar prestecs del lector amb id=" + idLector);
        List<Prestec> prestecsLector = new LinkedList<>();
        for (Prestec p : this.prestecs) {
            if (p.getIdLector().equals(idLector)) {
                prestecsLector.add(p);
            }
        }
        if (prestecsLector.isEmpty()) {
            logger.warn("El lector amb id=" + idLector + " no te cap prestec.");
            throw new LectorNotFoundException();
        }
        return prestecsLector;
    }
    @Override
    public void clear() {
        this.llibres.clear();
        this.lectors.clear();
        this.prestecs.clear();
        this.munt.clear();
    }
}
