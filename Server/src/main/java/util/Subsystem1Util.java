package util;

import command.Subsystem1Commands;
import util.validation.Subsystem1ValidationUtil;
import entities.Mesto;
import entities.Korisnik;
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
        String naziv = (String) request.get("naziv");
        
        Response validationError = validator.validateMestoData(naziv);
        if (validationError != null) {
            return validationError;
        }
        
        String command = Subsystem1Commands.CREATE_MESTO + ":" + naziv.trim();
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to create mesto").build();
        }
        
        // Check if result is Mesto object
        if (result instanceof Mesto) {
            return Response.status(201).entity(result).build();
        } else {
            return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
        }
    }
    
    public Response getAllMesta() {
        Object mesta = jmsUtil.sendCommandToSubsystem1(Subsystem1Commands.GET_ALL_MESTA);
        return mesta == null ? Response.status(404).entity("No mesta found").build() : Response.ok(mesta).build();
    }
    
    public Response getMestoById(int mestoId) {
        if (mestoId <= 0) {
            return Response.status(400).entity("Mesto ID must be a positive number").build();
        }
        
        String command = Subsystem1Commands.GET_MESTO_BY_ID + ":" + mestoId;
        Object mesto = jmsUtil.sendCommandToSubsystem1(command);
        
        return mesto == null ? Response.status(404).entity("Mesto with ID " + mestoId + " not found").build() : Response.ok(mesto).build();
    }
    
    // Korisnik operations
    public Response createKorisnik(Map<String, Object> request) {
        String ime = (String) request.get("ime");
        String email = (String) request.get("email");
        Object godisteObj = request.get("godiste");
        String pol = (String) request.get("pol");
        Object mestoIdObj = request.get("mestoId");
        
        Response validationError = validator.validateKorisnikData(ime, email, godisteObj, pol, mestoIdObj);
        if (validationError != null) {
            return validationError;
        }
        
        int godiste = ((Number) godisteObj).intValue();
        int mestoId = ((Number) mestoIdObj).intValue();
        String normalizedPol = pol.trim().toUpperCase();
        
        String command = Subsystem1Commands.CREATE_KORISNIK + ":" + 
                        ime.trim() + ":" + email.trim() + ":" + godiste + ":" + normalizedPol + ":" + mestoId;
        
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to create korisnik").build();
        }
        
        // Check if result is Korisnik object
        if (result instanceof Korisnik) {
            return Response.status(201).entity(result).build();
        } else {
            return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
        }
    }
    
    public Response getAllKorisnici() {
        Object korisnici = jmsUtil.sendCommandToSubsystem1(Subsystem1Commands.GET_ALL_KORISNICI);
        return korisnici == null ? Response.status(404).entity("No korisnici found").build() : Response.ok(korisnici).build();
    }
    
    public Response getKorisnikById(int korisnikId) {
        if (korisnikId <= 0) {
            return Response.status(400).entity("Korisnik ID must be a positive number").build();
        }
        
        String command = Subsystem1Commands.GET_KORISNIK_BY_ID + ":" + korisnikId;
        Object korisnik = jmsUtil.sendCommandToSubsystem1(command);
        
        return korisnik == null ? Response.status(404).entity("Korisnik with ID " + korisnikId + " not found").build() : Response.ok(korisnik).build();
    }
    
    public Response updateKorisnikEmail(int korisnikId, Map<String, Object> request) {
        if (korisnikId <= 0) {
            return Response.status(400).entity("Korisnik ID must be a positive number").build();
        }
        
        String email = (String) request.get("email");
        
        Response validationError = validator.validateEmailUpdate(email);
        if (validationError != null) {
            return validationError;
        }
        
        String command = Subsystem1Commands.UPDATE_KORISNIK_EMAIL + ":" + korisnikId + ":" + email.trim();
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to update email").build();
        }
        
        // Check if result is Korisnik object
        if (result instanceof Korisnik) {
            return Response.ok(result).build();
        } else {
            return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
        }
    }
    
    public Response updateKorisnikMesto(int korisnikId, Map<String, Object> request) {
        if (korisnikId <= 0) {
            return Response.status(400).entity("Korisnik ID must be a positive number").build();
        }
        
        Object mestoIdObj = request.get("mestoId");
        
        if (mestoIdObj == null) {
            return Response.status(400).entity("Mesto ID is required").build();
        }
        
        int mestoId;
        try {
            mestoId = ((Number) mestoIdObj).intValue();
            if (mestoId <= 0) {
                return Response.status(400).entity("Mesto ID must be a positive number").build();
            }
        } catch (Exception e) {
            return Response.status(400).entity("Mesto ID must be a valid number").build();
        }
        
        String command = Subsystem1Commands.UPDATE_KORISNIK_MESTO + ":" + korisnikId + ":" + mestoId;
        Object result = jmsUtil.sendCommandToSubsystem1(command);
        
        if (result == null) {
            return Response.status(500).entity("Failed to update mesto").build();
        }
        
        // Check if result is Korisnik object
        if (result instanceof Korisnik) {
            return Response.ok(result).build();
        } else {
            return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
        }
    }
    
    public Response testSubsystem1() {
        Object response = jmsUtil.sendCommandToSubsystem1(Subsystem1Commands.TEST_MESSAGE);
        return response == null ? Response.status(500).entity("No response from subsystem1").build() : Response.ok(response).build();
    }
}