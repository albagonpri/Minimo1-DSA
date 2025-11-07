package edu.upc.dsa;

import edu.upc.dsa.exceptions.LlibreNotFoundException;
import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Prestec;
import edu.upc.dsa.models.Lector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ManagerImplTest {
    Manager tm;

    @Before
    public void setUp() {
        this.tm = ManagerImpl.getInstance();
        this.tm.afegirLector("1", "Alba", "Gonzalez Prieto", 44662676, 21.01.2003, "badalona");
        this.tm.afegirLector("2", "Adrian", "Aira Saco", 1234567, 28.05.2003, "lugo");
    }

    @After
    public void tearDown() {
        // És un Singleton
        this.tm.clear();
    }

    @Test
    public void addLlibre() {
        Assert.assertEquals(3, tm.size());

        this.tm.addTrack("La Vereda De La Puerta De Atrás", "Extremoduro");

        Assert.assertEquals(4, tm.size());

    }

    @Test
    public void getTrackTest() throws Exception {
        Assert.assertEquals(3, tm.size());

        Llibre t = this.tm.getTrack("T2");
        Assert.assertEquals("Despacito", t.getTitle());
        Assert.assertEquals("Luis Fonsi", t.getSinger());

        t = this.tm.getTrack2("T2");
        Assert.assertEquals("Despacito", t.getTitle());
        Assert.assertEquals("Luis Fonsi", t.getSinger());

        Assert.assertThrows(LlibreNotFoundException.class, () ->
                this.tm.getTrack2("XXXXXXX"));

    }

    @Test
    public void getTracksTest() {
        Assert.assertEquals(3, tm.size());
        List<Llibre> llibres = tm.findAll();

        Llibre t = llibres.get(0);
        Assert.assertEquals("La Barbacoa", t.getTitle());
        Assert.assertEquals("Georgie Dann", t.getSinger());

        t = llibres.get(1);
        Assert.assertEquals("Despacito", t.getTitle());
        Assert.assertEquals("Luis Fonsi", t.getSinger());

        t = llibres.get(2);
        Assert.assertEquals("Ent3r S4ndm4n", t.getTitle());
        Assert.assertEquals("Metallica", t.getSinger());

        Assert.assertEquals(3, tm.size());

    }

    @Test
    public void updateTrackTest() {
        Assert.assertEquals(3, tm.size());
        Llibre t = this.tm.getTrack("T3");
        Assert.assertEquals("Ent3r S4ndm4n", t.getTitle());
        Assert.assertEquals("Metallica", t.getSinger());

        t.setTitle("Enter Sandman");
        this.tm.updateTrack(t);

        t = this.tm.getTrack("T3");
        Assert.assertEquals("Enter Sandman", t.getTitle());
        Assert.assertEquals("Metallica", t.getSinger());
    }


    @Test
    public void deleteTrackTest() {
        Assert.assertEquals(3, tm.size());
        this.tm.deleteTrack("T3");
        Assert.assertEquals(2, tm.size());

        Assert.assertThrows(LlibreNotFoundException.class, () ->
                this.tm.getTrack2("T3"));

    }
}
