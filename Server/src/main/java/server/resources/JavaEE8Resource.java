package server.resources;

import util.JMSUtil;
import command.Subsystem1Commands;
import javax.inject.Inject;
import javax.ws.rs.GET;
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
}
