package edu.upc.dsa.services;

import edu.upc.dsa.Manager;
import edu.upc.dsa.ManagerImpl;
import edu.upc.dsa.exceptions.LectorNotFoundException;
import edu.upc.dsa.exceptions.LlibreNotFoundException;
import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.Llibre;
import edu.upc.dsa.models.Prestec;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/biblio", description = "Endpoint to Biblio Service")
@Path("/biblio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BiblioService {

    private final Manager manager;

    public BiblioService() {
        this.manager = ManagerImpl.getInstance();
        try {
            if (this.manager.size() == 0) {
                this.manager.afegirLector("1", "Alba", "Gonzalez", "44662676B", "21.01.2003", "Ourense", "Badalona");
                this.manager.emmagatzemarLlibre("JV7d", "978-1605062234", "Te Steam House", "Forgotten Books", 1880, 1, "Jules Verne", "Adventues");
            }
        } catch (Exception ignored) {}
    }

    @POST
    @ApiOperation(value = "Nou lector")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Nou lector afegit correctament.", response = Lector.class),
            @ApiResponse(code = 400, message = "Falta informacio")
    })
    @Path("/lectors")
    public Response addLector(Lector lector) {
        if (lector == null || lector.getIdLector() == null || lector.getNom() == null ||
                lector.getCognoms() == null || lector.getDNI() == null ||
                lector.getData_neix() == null || lector.getLloc_neix() == null || lector.getAddress() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Falta informacio").build();
        }
        Lector out = manager.afegirLector(
                lector.getIdLector(), lector.getNom(), lector.getCognoms(),
                lector.getDNI(), lector.getData_neix(), lector.getLloc_neix(), lector.getAddress()
        );
        return Response.status(Response.Status.CREATED).entity(out).build();
    }

    @POST
    @ApiOperation(value = "Emmagatzemar llibre")
    @ApiResponses({
            @ApiResponse(code = 201, message = "S'ha emmagatzemat el llibre"),
            @ApiResponse(code = 400, message = "Falta informacio")
    })
    @Path("/munt/llibres")
    public Response emmagatzemarLlibre(Llibre llibre) {
        if (llibre == null || llibre.getId() == null || llibre.getISBN() == null || llibre.getTitol() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Falta informacio").build();
        }
        try {
            Llibre out = manager.emmagatzemarLlibre(
                    llibre.getId(), llibre.getISBN(), llibre.getTitol(), llibre.getEditorial(),
                    llibre.getAny_publicacio(), llibre.getNumedicio(), llibre.getAutor(), llibre.getTematica()
            );
            return Response.status(Response.Status.CREATED).entity(out).build();
        } catch (LlibreNotFoundException e) {
            return Response.serverError().entity("Error").build();
        }
    }

    @POST
    @ApiOperation(value = "Catalogar llibre")
    @ApiResponses({
            @ApiResponse(code = 200, message = "S'ha catalogat correctament", response = Llibre.class),
            @ApiResponse(code = 404, message = "No queden llibres")
    })
    @Path("/munt/catalogar")
    public Response catalogarLlibre() {
        try {
            Llibre llibreCatalogat = manager.catalogarLlibre();
            return Response.ok(llibreCatalogat).build();
        } catch (LlibreNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("No queden llibres").build();
        }
    }

    @POST
    @ApiOperation(value = "Prestec")
    @ApiResponses({
            @ApiResponse(code = 201, message = "S'ha realitzat correctament", response = Prestec.class),
            @ApiResponse(code = 400, message = "Falta informacio"),
            @ApiResponse(code = 404, message = "Lector o llibre no trobat"),
            @ApiResponse(code = 409, message = "No hi ha exemplars disponibles")
    })
    @Path("/prestecs")
    public Response prestarLlibre(Prestec prestec) {
        if (prestec == null || prestec.getIdPrestec() == null || prestec.getIdLector() == null || prestec.getIdLlibre() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Falta informacio").build();
        }
        try {
            Prestec p = manager.prestarLlibre(
                    prestec.getIdPrestec(), prestec.getIdLector(), prestec.getIdLlibre(),
                    prestec.getData_inici(), prestec.getData_fi()
            );
            return Response.status(Response.Status.CREATED).entity(p).build();
        } catch (LectorNotFoundException | LlibreNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Lector o llibre no trobat").build();
        }
    }

    @GET
    @ApiOperation(value = "Consultar prestecs")
    @ApiResponses({
            @ApiResponse(code = 200, message = "S'ha consultat correctament", response = Prestec.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Lector no trobat")
    })
    @Path("/prestecs/{lectorId}")
    public Response consultarPrestecsLector(@PathParam("lectorId") String lectorId) {
        try {
            List<Prestec> prestecs = manager.consultarPrestecs(lectorId);
            GenericEntity<List<Prestec>> entity = new GenericEntity<List<Prestec>>(prestecs) {};
            return Response.ok(entity).build();
        } catch (LectorNotFoundException | LlibreNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Lector no trobat").build();
        }
    }

    @POST
    @Path("/clear")
    public Response clear() {
        manager.clear();
        return Response.ok().build();
    }

    @GET
    @Path("/size")
    public Response size() {
        return Response.ok(manager.size()).build();
    }
}
