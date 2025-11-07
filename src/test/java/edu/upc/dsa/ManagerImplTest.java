package edu.upc.dsa;

import edu.upc.dsa.exceptions.LectorNotFoundException;
import edu.upc.dsa.exceptions.LlibreNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.Prestec;

import java.util.List;

import static org.junit.Assert.*;

public class ManagerImplTest {
    private Manager manager;

    @Before
    public void setUp() throws LlibreNotFoundException {
        this.manager = ManagerImpl.getInstance();

        manager.afegirLector("1", "Alba", "Gonzalez", "44662676", "21.01.2003", "Ourense", "Badalona");

        manager.emmagatzemarLlibre("JV7d", "978-1605062234", "The Steam House", "Forgotten Books", 1880, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV4a", "978-1435149408", "The Mysterious Island", "Barnes & Noble Classics", 1874, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV1", "978-0486268685", "Journey to the Center of the Earth", "Dover Publications", 1864, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV3", "978-1516887907", "Around the World in Eighty Days", "CreateSpace", 1872, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV4c", "978-1435149408", "The Mysterious Island", "Barnes & Noble Classics", 1874, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV8", "978-1103325575", "The Begum's Fortune", "BiblioBazaar", 1879, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV7c", "978-1605062234", "The Steam House", "Forgotten Books", 1880, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV5", "978-1853260257", "The Adventures of Captain Hatteras", "Wordsworth Editions", 1866, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV2b", "978-0451530960", "Twenty Thousand Leagues Under the Sea", "Signet Classics", 1870, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV2c", "978-0451530960", "Twenty Thousand Leagues Under the Sea", "Signet Classics", 1870, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV2a", "978-0451530960", "Twenty Thousand Leagues Under the Sea", "Signet Classics", 1870, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV6", "978-0199538474", "From the Earth to the Moon", "Oxford University Press", 1865, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV7a", "978-1605062234", "The Steam House", "Forgotten Books", 1880, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV4b", "978-1435149408", "The Mysterious Island", "Barnes & Noble Classics", 1874, 1, "Jules Verne", "Adventures");
        manager.emmagatzemarLlibre("JV7b", "978-1605062234", "The Steam House", "Forgotten Books", 1880, 1, "Jules Verne", "Adventures");
    }

    @After
    public void tearDown(){
        this.manager.clear();
    }
    @Test
    public void testAfegirLector() {
        assertEquals(1,manager.size());
        Lector nou=manager.afegirLector("2","Taylor", "Swift", "123456", "13.10.1989", "Pennsylvania", "NY");
        assertNotNull(nou);
        assertEquals(2, manager.size());
        assertEquals("Taylor", nou.getNom());
    }
    @Test
    public void testEmmagatzemarLlibre() throws LlibreNotFoundException {
        Llibre llibre= manager.emmagatzemarLlibre("JV4", "978-1435149408", "The Mysterious Island", "Barnes & Noble Classics", 1874, 1, "Jules Verne", "Adventures");
        assertNotNull(llibre);
        assertEquals("JV4", llibre.getId());
    }
    @Test
    public  void testCatalogarLlibre() throws LlibreNotFoundException {
        int inicial= ((ManagerImpl)manager).llibresCatalogats.size();
        Llibre cat= manager.catalogarLlibre();
        assertNotNull(cat);
        assertEquals(inicial +1, ((ManagerImpl)manager).llibresCatalogats.size());
    }

    @Test
    public void testPrestarLlibre() throws LlibreNotFoundException, LectorNotFoundException {
        Llibre llibre =manager.catalogarLlibre();
        Prestec p= manager.prestarLlibre("p1", "1", llibre.getId(), "07.11.2025", "07.12.2025");
        assertNotNull(p);
        assertEquals("1", p.getIdLector());
        assertEquals(llibre.getId(), p.getIdLlibre());
    }
    @Test
    public void testConsultarPrestecs() throws LlibreNotFoundException, LectorNotFoundException {
        Llibre llibre =manager.catalogarLlibre();
        manager.prestarLlibre("p1", "1", llibre.getId(), "07.11.2025", "07.12.2025");
        List<Prestec> prestecs= manager.consultarPrestecs("1");
        assertEquals("p1", prestecs.get(0).getIdPrestec());
    }
    @Test(expected = LlibreNotFoundException.class)
    public void testCatalogarLlibreSenseLlibres() throws LlibreNotFoundException {
        manager.clear();
        manager.catalogarLlibre();
    }
    @Test(expected = LectorNotFoundException.class)
    public void testPrestarSenseLector() throws LlibreNotFoundException, LectorNotFoundException {
        Llibre llibre = manager.catalogarLlibre();
        manager.prestarLlibre("p1", "3", llibre.getId(), "07.11.2025", "07.12.2025");
    }

    @Test(expected = LlibreNotFoundException.class)
    public void testPrestarLlibreSense() throws LlibreNotFoundException, LectorNotFoundException {
        manager.prestarLlibre("p1", "1", "jsshaj", "07.11.2025", "07.12.2025");
    }

}