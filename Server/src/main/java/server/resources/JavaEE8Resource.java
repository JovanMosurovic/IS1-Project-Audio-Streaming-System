package server.resources;

import util.JMSUtil;
import command.Subsystem1Commands;
import entities.Korisnik;
import entities.Mesto;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("")
public class JavaEE8Resource {
    @Inject
    private JMSUtil jmsUtil;
    
    @GET
    public Response ping() {
        System.out.println("[INFO] Server - Ping request received");
        return Response.ok("Server is running").build();
    }
    
    // Create new mesto
    @POST
    @Path("mesto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMesto(Mesto mesto) {
        System.out.println("[INFO] Server - POST create mesto: " + mesto.getNaziv() + " -> Subsystem1");
        
        if (mesto.getNaziv() == null || mesto.getNaziv().trim().isEmpty()) {
            return Response.status(400).entity("Mesto naziv is required").build();
        }
        
        String command = Subsystem1Commands.CREATE_MESTO + ":" + mesto.getNaziv();
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to create mesto").build();
        }
        
        String resultStr = result.toString();
        if (resultStr.startsWith("SUCCESS")) {
            return Response.status(201).entity(resultStr).build();
        } else {
            return Response.status(500).entity(resultStr).build();
        }
    }
    
    // Create new korisnik
    @POST
    @Path("korisnik")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createKorisnik(Korisnik korisnik) {
        System.out.println("[INFO] Server - POST create korisnik: " + korisnik.getIme() + " -> Subsystem1");
        
        // Validate required fields
        if (korisnik.getIme() == null || korisnik.getIme().trim().isEmpty()) {
            return Response.status(400).entity("Korisnik ime is required").build();
        }
        if (korisnik.getEmail() == null || korisnik.getEmail().trim().isEmpty()) {
            return Response.status(400).entity("Korisnik email is required").build();
        }
        if (korisnik.getGodiste() <= 0) {
            return Response.status(400).entity("Korisnik godiste is required").build();
        }
        if (korisnik.getPol() == null || korisnik.getPol().trim().isEmpty()) {
            return Response.status(400).entity("Korisnik pol is required").build();
        }
        if (korisnik.getMestoId() == null || korisnik.getMestoId().getMestoId() == null) {
            return Response.status(400).entity("Korisnik mesto_id is required").build();
        }
        
        // Create command with all parameters
        String command = Subsystem1Commands.CREATE_KORISNIK + ":" + 
                        korisnik.getIme() + ":" + 
                        korisnik.getEmail() + ":" + 
                        korisnik.getGodiste() + ":" + 
                        korisnik.getPol() + ":" + 
                        korisnik.getMestoId().getMestoId();
        
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to create korisnik").build();
        }
        
        String resultStr = result.toString();
        if (resultStr.startsWith("SUCCESS")) {
            return Response.status(201).entity(resultStr).build();
        } else {
            return Response.status(500).entity(resultStr).build();
        }
    }
    
    // Get all mesta
    @GET
    @Path("mesto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMesta() {
        System.out.println("[INFO] Server - GET all mesta request -> Subsystem1");
        Object mesta = jmsUtil.sendCommandToSubsystem1(Subsystem1Commands.GET_ALL_MESTA);
        
        if (mesta == null) {
            return Response.status(404).entity("No data received").build();
        }
        
        return Response.ok(mesta).build();
    }
    
    // Get all korisnici
    @GET
    @Path("korisnik")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKorisnici() {
        System.out.println("[INFO] Server - GET all korisnici request -> Subsystem1");
        Object korisnici = jmsUtil.sendCommandToSubsystem1(Subsystem1Commands.GET_ALL_KORISNICI);
        
        if (korisnici == null) {
            return Response.status(404).entity("No data received").build();
        }
        
        return Response.ok(korisnici).build();
    }
    
    // Get mesto by ID
    @GET
    @Path("mesto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMestoById(@PathParam("id") int mestoId) {
        System.out.println("[INFO] Server - GET mesto by ID: " + mestoId + " -> Subsystem1");
        String command = Subsystem1Commands.GET_MESTO_BY_ID + ":" + mestoId;
        Object mesto = jmsUtil.sendCommandToSubsystem1(command);
        
        if (mesto == null) {
            return Response.status(404).entity("Mesto not found").build();
        }
        
        return Response.ok(mesto).build();
    }
    
    // Get korisnik by ID
    @GET
    @Path("korisnik/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKorisnikById(@PathParam("id") int korisnikId) {
        System.out.println("[INFO] Server - GET korisnik by ID: " + korisnikId + " -> Subsystem1");
        String command = Subsystem1Commands.GET_KORISNIK_BY_ID + ":" + korisnikId;
        Object korisnik = jmsUtil.sendCommandToSubsystem1(command);
        
        if (korisnik == null) {
            return Response.status(404).entity("Korisnik not found").build();
        }
        
        return Response.ok(korisnik).build();
    }
    
    // Test endpoint
    @GET
    @Path("test/subsystem1")
    public Response testSubsystem1() {
        System.out.println("[INFO] Server - Test Subsystem1");
        Object response = jmsUtil.sendCommandToSubsystem1(Subsystem1Commands.TEST_MESSAGE);
        
        if (response == null) {
            return Response.status(404).entity("No response").build();
        }
        
        return Response.ok(response).build();
    }
    
    @POST
@Path("test")
@Produces(MediaType.TEXT_PLAIN)
public Response testPost() {
    return Response.ok("POST method works!").build();
}
}
