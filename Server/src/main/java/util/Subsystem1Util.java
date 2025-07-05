package util;

import command.Subsystem1Commands;
import util.validation.Subsystem1ValidationUtil;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class Subsystem1Util {
    
    @Inject
    private JMSUtil jmsUtil;
    
    @Inject
    private Subsystem1ValidationUtil validator;
    
    // Mesto operations
    public Response createMesto(Map<String, Object> request) {
        System.out.println("[INFO] Subsystem1Utils - Processing create mesto request");
        
        String naziv = (String) request.get("naziv");
        
        // Validate input
        Response validationError = validator.validateMestoData(naziv);
        if (validationError != null) {
            return validationError;
        }
        
        String command = Subsystem1Commands.CREATE_MESTO + ":" + naziv.trim();
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to create mesto").build();
        }
        
        String resultStr = result.toString();
        if (resultStr.startsWith("SUCCESS")) {
            return Response.status(201).entity(resultStr).build();
        } else if (resultStr.contains("already exists") || resultStr.contains("duplicate")) {
            return Response.status(409).entity("Mesto with this naziv already exists").build();
        } else {
            return Response.status(500).entity(resultStr).build();
        }
    }
    
    public Response getAllMesta() {
        System.out.println("[INFO] Subsystem1Utils - Processing get all mesta request");
        
        Object mesta = jmsUtil.sendCommandToSubsystem1(Subsystem1Commands.GET_ALL_MESTA);
        
        if (mesta == null) {
            return Response.status(404).entity("No mesta found").build();
        }
        
        return Response.ok(mesta).build();
    }
    
    public Response getMestoById(int mestoId) {
        System.out.println("[INFO] Subsystem1Utils - Processing get mesto by ID: " + mestoId);
        
        if (mestoId <= 0) {
            return Response.status(400).entity("Mesto ID must be a positive number").build();
        }
        
        String command = Subsystem1Commands.GET_MESTO_BY_ID + ":" + mestoId;
        Object mesto = jmsUtil.sendCommandToSubsystem1(command);
        
        if (mesto == null) {
            return Response.status(404).entity("Mesto with ID " + mestoId + " not found").build();
        }
        
        return Response.ok(mesto).build();
    }
    
    // Korisnik operations
    public Response createKorisnik(Map<String, Object> request) {
        System.out.println("[INFO] Subsystem1Utils - Processing create korisnik request");
        
        // Extract parameters
        String ime = (String) request.get("ime");
        String email = (String) request.get("email");
        Object godisteObj = request.get("godiste");
        String pol = (String) request.get("pol");
        Object mestoIdObj = request.get("mestoId");
        
        // Validate input
        Response validationError = validator.validateKorisnikData(ime, email, godisteObj, pol, mestoIdObj);
        if (validationError != null) {
            return validationError;
        }
        
        int godiste = ((Number) godisteObj).intValue();
        int mestoId = ((Number) mestoIdObj).intValue();
        
        // Normalize pol to uppercase
        String normalizedPol = pol.trim().toUpperCase();
        
        // Create command with all parameters
        String command = Subsystem1Commands.CREATE_KORISNIK + ":" + 
                        ime.trim() + ":" + email.trim() + ":" + godiste + ":" + normalizedPol + ":" + mestoId;
        
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to create korisnik").build();
        }
        
        String resultStr = result.toString();
        if (resultStr.startsWith("SUCCESS")) {
            return Response.status(201).entity(resultStr).build();
        } else if (resultStr.contains("already exists") || resultStr.contains("duplicate") || resultStr.contains("email")) {
            return Response.status(409).entity("Korisnik with this email already exists").build();
        } else if (resultStr.contains("mesto") && resultStr.contains("not found")) {
            return Response.status(400).entity("Specified mesto does not exist").build();
        } else {
            return Response.status(500).entity(resultStr).build();
        }
    }
    
    public Response getAllKorisnici() {
        System.out.println("[INFO] Subsystem1Utils - Processing get all korisnici request");
        
        Object korisnici = jmsUtil.sendCommandToSubsystem1(Subsystem1Commands.GET_ALL_KORISNICI);
        
        if (korisnici == null) {
            return Response.status(404).entity("No korisnici found").build();
        }
        
        return Response.ok(korisnici).build();
    }
    
    public Response getKorisnikById(int korisnikId) {
        System.out.println("[INFO] Subsystem1Utils - Processing get korisnik by ID: " + korisnikId);
        
        if (korisnikId <= 0) {
            return Response.status(400).entity("Korisnik ID must be a positive number").build();
        }
        
        String command = Subsystem1Commands.GET_KORISNIK_BY_ID + ":" + korisnikId;
        Object korisnik = jmsUtil.sendCommandToSubsystem1(command);
        
        if (korisnik == null) {
            return Response.status(404).entity("Korisnik with ID " + korisnikId + " not found").build();
        }
        
        return Response.ok(korisnik).build();
    }
    
    public Response updateKorisnikEmail(int korisnikId, Map<String, Object> request) {
        System.out.println("[INFO] Subsystem1Utils - Processing update korisnik email, ID: " + korisnikId);
        
        if (korisnikId <= 0) {
            return Response.status(400).entity("Korisnik ID must be a positive number").build();
        }
        
        String email = (String) request.get("email");
        
        // Validate email
        Response validationError = validator.validateEmailUpdate(email);
        if (validationError != null) {
            return validationError;
        }
        
        String command = Subsystem1Commands.UPDATE_KORISNIK_EMAIL + ":" + korisnikId + ":" + email.trim();
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to update email").build();
        }
        
        String resultStr = result.toString();
        if (resultStr.startsWith("SUCCESS")) {
            return Response.ok(resultStr).build();
        } else if (resultStr.contains("not found")) {
            return Response.status(404).entity("Korisnik with ID " + korisnikId + " not found").build();
        } else if (resultStr.contains("already exists") || resultStr.contains("duplicate")) {
            return Response.status(409).entity("Email already exists for another korisnik").build();
        } else {
            return Response.status(500).entity(resultStr).build();
        }
    }
    
    public Response updateKorisnikMesto(int korisnikId, Map<String, Object> request) {
        System.out.println("[INFO] Subsystem1Utils - Processing update korisnik mesto, ID: " + korisnikId);
        
        if (korisnikId <= 0) {
            return Response.status(400).entity("Korisnik ID must be a positive number").build();
        }
        
        Object mestoIdObj = request.get("mestoId");
        
        // Validate mesto ID
        Response validationError = validator.validateMestoIdUpdate(mestoIdObj);
        if (validationError != null) {
            return validationError;
        }
        
        int mestoId = ((Number) mestoIdObj).intValue();
        
        String command = Subsystem1Commands.UPDATE_KORISNIK_MESTO + ":" + korisnikId + ":" + mestoId;
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to update mesto").build();
        }
        
        String resultStr = result.toString();
        if (resultStr.startsWith("SUCCESS")) {
            return Response.ok(resultStr).build();
        } else if (resultStr.contains("korisnik") && resultStr.contains("not found")) {
            return Response.status(404).entity("Korisnik with ID " + korisnikId + " not found").build();
        } else if (resultStr.contains("mesto") && resultStr.contains("not found")) {
            return Response.status(400).entity("Mesto with ID " + mestoId + " not found").build();
        } else {
            return Response.status(500).entity(resultStr).build();
        }
    }
    
    // Test operation
    public Response testSubsystem1() {
        System.out.println("[INFO] Subsystem1Utils - Processing test subsystem1 request");
        
        Object response = jmsUtil.sendCommandToSubsystem1(Subsystem1Commands.TEST_MESSAGE);
        
        if (response == null) {
            return Response.status(500).entity("No response from subsystem1").build();
        }
        
        return Response.ok(response).build();
    }
}