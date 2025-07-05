package server.resources;

import util.Subsystem1Util;
import util.Subsystem2Util;
import util.Subsystem3Util;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("")
public class JavaEE8Resource {
    
    @Inject
    private Subsystem1Util subsystem1Util;
    
    @Inject
    private Subsystem2Util subsystem2Util;
    
    @Inject
    private Subsystem3Util subsystem3Util;
    
    @GET
    public Response ping() {
        System.out.println("[INFO] Server - Ping request received");
        return Response.ok("Server is running").build();
    }
    
    // ============== SUBSYSTEM 1 ENDPOINTS ==============
    
    @POST
    @Path("mesto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMesto(Map<String, Object> request) {
        return subsystem1Util.createMesto(request);
    }
    
    @GET
    @Path("mesto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMesta() {
        return subsystem1Util.getAllMesta();
    }
    
    @GET
    @Path("mesto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMestoById(@PathParam("id") int mestoId) {
        return subsystem1Util.getMestoById(mestoId);
    }
    
    @POST
    @Path("korisnik")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createKorisnik(Map<String, Object> request) {
        return subsystem1Util.createKorisnik(request);
    }
    
    @GET
    @Path("korisnik")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKorisnici() {
        return subsystem1Util.getAllKorisnici();
    }
    
    @GET
    @Path("korisnik/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKorisnikById(@PathParam("id") int korisnikId) {
        return subsystem1Util.getKorisnikById(korisnikId);
    }
    
    @PUT
    @Path("korisnik/{id}/email")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKorisnikEmail(@PathParam("id") int korisnikId, Map<String, Object> request) {
        return subsystem1Util.updateKorisnikEmail(korisnikId, request);
    }
    
    @PUT
    @Path("korisnik/{id}/mesto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKorisnikMesto(@PathParam("id") int korisnikId, Map<String, Object> request) {
        return subsystem1Util.updateKorisnikMesto(korisnikId, request);
    }
    
    // ============== SUBSYSTEM 2 ENDPOINTS ==============
    
    //
    
    // ============== SUBSYSTEM 3 ENDPOINTS ==============
    
    //
    
}
