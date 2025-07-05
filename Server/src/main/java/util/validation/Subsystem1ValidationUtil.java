package util.validation;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class Subsystem1ValidationUtil {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    private static final List<String> VALID_POL_VALUES = Arrays.asList("MUSKI", "ZENSKI", "DRUGO");
    
    // Mesto validation
    public Response validateMestoData(String naziv) {
        if (naziv == null || naziv.trim().isEmpty()) {
            return Response.status(400).entity("Mesto naziv is required").build();
        }
        
        if (naziv.length() > 100) {
            return Response.status(400).entity("Mesto naziv must be less than 100 characters").build();
        }
        
        if (naziv.trim().length() == 0) {
            return Response.status(400).entity("Mesto naziv cannot be only whitespace").build();
        }
        
        return null;
    }
    
    // Korisnik validation
    public Response validateKorisnikData(String ime, String email, Object godisteObj, String pol, Object mestoIdObj) {
        // Validate ime
        if (ime == null || ime.trim().isEmpty()) {
            return Response.status(400).entity("Korisnik ime is required").build();
        }
        if (ime.length() > 100) {
            return Response.status(400).entity("Korisnik ime must be less than 100 characters").build();
        }
        if (ime.trim().length() == 0) {
            return Response.status(400).entity("Korisnik ime cannot be only whitespace").build();
        }
        
        // Validate email
        if (email == null || email.trim().isEmpty()) {
            return Response.status(400).entity("Korisnik email is required").build();
        }
        if (email.length() > 100) {
            return Response.status(400).entity("Korisnik email must be less than 100 characters").build();
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return Response.status(400).entity("Korisnik email format is invalid").build();
        }
        
        // Validate godiste
        if (godisteObj == null) {
            return Response.status(400).entity("Korisnik godiste is required").build();
        }
        
        int godiste;
        try {
            godiste = ((Number) godisteObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Korisnik godiste must be a valid number").build();
        }
        
        int currentYear = java.time.Year.now().getValue();
        if (godiste < 1900 || godiste > currentYear) {
            return Response.status(400).entity("Korisnik godiste must be between 1900 and " + currentYear).build();
        }
        
        // Validate pol
        if (pol == null || pol.trim().isEmpty()) {
            return Response.status(400).entity("Korisnik pol is required").build();
        }
        if (!VALID_POL_VALUES.contains(pol.trim().toUpperCase())) {
            return Response.status(400).entity("Korisnik pol must be one of: " + String.join(", ", VALID_POL_VALUES)).build();
        }
        
        // Validate mesto_id
        if (mestoIdObj == null) {
            return Response.status(400).entity("Korisnik mestoId is required").build();
        }
        
        int mestoId;
        try {
            mestoId = ((Number) mestoIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Korisnik mestoId must be a valid number").build();
        }
        
        if (mestoId <= 0) {
            return Response.status(400).entity("Korisnik mestoId must be a positive number").build();
        }
        
        return null;
    }
    
    // Email validation for updates
    public Response validateEmailUpdate(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Response.status(400).entity("Email is required").build();
        }
        if (email.length() > 100) {
            return Response.status(400).entity("Email must be less than 100 characters").build();
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return Response.status(400).entity("Email format is invalid").build();
        }
        
        return null;
    }
    
    // Mesto ID validation for updates
    public Response validateMestoIdUpdate(Object mestoIdObj) {
        if (mestoIdObj == null) {
            return Response.status(400).entity("Mesto ID is required").build();
        }
        
        int mestoId;
        try {
            mestoId = ((Number) mestoIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Mesto ID must be a valid number").build();
        }
        
        if (mestoId <= 0) {
            return Response.status(400).entity("Mesto ID must be a positive number").build();
        }
        
        return null;
    }
}