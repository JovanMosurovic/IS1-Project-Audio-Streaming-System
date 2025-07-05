package util.validation;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class Subsystem3ValidationUtil {
    
    // Pretplata validation
    public Response validatePretplataData(Object korisnikIdObj, Object paketIdObj, Object placenaCenaObj) {
        // Validate korisnikId
        if (korisnikIdObj == null) {
            return Response.status(400).entity("Pretplata korisnikId is required").build();
        }
        
        int korisnikId;
        try {
            korisnikId = ((Number) korisnikIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Pretplata korisnikId must be a valid number").build();
        }
        
        if (korisnikId <= 0) {
            return Response.status(400).entity("Pretplata korisnikId must be a positive number").build();
        }
        
        // Validate paketId
        if (paketIdObj == null) {
            return Response.status(400).entity("Pretplata paketId is required").build();
        }
        
        int paketId;
        try {
            paketId = ((Number) paketIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Pretplata paketId must be a valid number").build();
        }
        
        if (paketId <= 0) {
            return Response.status(400).entity("Pretplata paketId must be a positive number").build();
        }
        
        // Validate placenaCena
        if (placenaCenaObj == null) {
            return Response.status(400).entity("Pretplata placenaCena is required").build();
        }
        
        double placenaCena;
        try {
            placenaCena = ((Number) placenaCenaObj).doubleValue();
        } catch (Exception e) {
            return Response.status(400).entity("Pretplata placenaCena must be a valid number").build();
        }
        
        if (placenaCena <= 0) {
            return Response.status(400).entity("Pretplata placenaCena must be greater than 0").build();
        }
        
        return null;
    }
    
    // Paket validation
    public Response validatePaketData(Object trenutnaCenaObj) {
        if (trenutnaCenaObj == null) {
            return Response.status(400).entity("Paket trenutnaCena is required").build();
        }
        
        double trenutnaCena;
        try {
            trenutnaCena = ((Number) trenutnaCenaObj).doubleValue();
        } catch (Exception e) {
            return Response.status(400).entity("Paket trenutnaCena must be a valid number").build();
        }
        
        if (trenutnaCena <= 0) {
            return Response.status(400).entity("Paket trenutnaCena must be greater than 0").build();
        }
        
        return null;
    }
    
    // Ocena validation
    public Response validateOcenaData(Object korisnikIdObj, Object audioIdObj, Object vrednostObj) {
        // Validate korisnikId
        if (korisnikIdObj == null) {
            return Response.status(400).entity("Ocena korisnikId is required").build();
        }
        
        int korisnikId;
        try {
            korisnikId = ((Number) korisnikIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Ocena korisnikId must be a valid number").build();
        }
        
        if (korisnikId <= 0) {
            return Response.status(400).entity("Ocena korisnikId must be a positive number").build();
        }
        
        // Validate audioId
        if (audioIdObj == null) {
            return Response.status(400).entity("Ocena audioId is required").build();
        }
        
        int audioId;
        try {
            audioId = ((Number) audioIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Ocena audioId must be a valid number").build();
        }
        
        if (audioId <= 0) {
            return Response.status(400).entity("Ocena audioId must be a positive number").build();
        }
        
        // Validate vrednost (1-5)
        if (vrednostObj == null) {
            return Response.status(400).entity("Ocena vrednost is required").build();
        }
        
        int vrednost;
        try {
            vrednost = ((Number) vrednostObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Ocena vrednost must be a valid number").build();
        }
        
        if (vrednost < 1 || vrednost > 5) {
            return Response.status(400).entity("Ocena vrednost must be between 1 and 5").build();
        }
        
        return null;
    }
    
    // Istorija slusanja validation
    public Response validateIstorijaData(Object korisnikIdObj, Object audioIdObj, Object pocetniSekObj, Object brojSekObj) {
        // Validate korisnikId
        if (korisnikIdObj == null) {
            return Response.status(400).entity("Istorija korisnikId is required").build();
        }
        
        int korisnikId;
        try {
            korisnikId = ((Number) korisnikIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Istorija korisnikId must be a valid number").build();
        }
        
        if (korisnikId <= 0) {
            return Response.status(400).entity("Istorija korisnikId must be a positive number").build();
        }
        
        // Validate audioId
        if (audioIdObj == null) {
            return Response.status(400).entity("Istorija audioId is required").build();
        }
        
        int audioId;
        try {
            audioId = ((Number) audioIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Istorija audioId must be a valid number").build();
        }
        
        if (audioId <= 0) {
            return Response.status(400).entity("Istorija audioId must be a positive number").build();
        }
        
        // Validate pocetniSekund
        if (pocetniSekObj == null) {
            return Response.status(400).entity("Istorija pocetniSekund is required").build();
        }
        
        int pocetniSekund;
        try {
            pocetniSekund = ((Number) pocetniSekObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Istorija pocetniSekund must be a valid number").build();
        }
        
        if (pocetniSekund < 0) {
            return Response.status(400).entity("Istorija pocetniSekund must be 0 or greater").build();
        }
        
        // Validate brojOdslusanihSekundi
        if (brojSekObj == null) {
            return Response.status(400).entity("Istorija brojOdslusanihSekundi is required").build();
        }
        
        int brojOdslusanihSekundi;
        try {
            brojOdslusanihSekundi = ((Number) brojSekObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Istorija brojOdslusanihSekundi must be a valid number").build();
        }
        
        if (brojOdslusanihSekundi < 0) {
            return Response.status(400).entity("Istorija brojOdslusanihSekundi must be 0 or greater").build();
        }
        
        return null;
    }
    
    // Omiljeni audio validation
    public Response validateOmiljeniAudioData(Object korisnikIdObj, Object audioIdObj) {
        // Validate korisnikId
        if (korisnikIdObj == null) {
            return Response.status(400).entity("Omiljeni audio korisnikId is required").build();
        }
        
        int korisnikId;
        try {
            korisnikId = ((Number) korisnikIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Omiljeni audio korisnikId must be a valid number").build();
        }
        
        if (korisnikId <= 0) {
            return Response.status(400).entity("Omiljeni audio korisnikId must be a positive number").build();
        }
        
        // Validate audioId
        if (audioIdObj == null) {
            return Response.status(400).entity("Omiljeni audio audioId is required").build();
        }
        
        int audioId;
        try {
            audioId = ((Number) audioIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Omiljeni audio audioId must be a valid number").build();
        }
        
        if (audioId <= 0) {
            return Response.status(400).entity("Omiljeni audio audioId must be a positive number").build();
        }
        
        return null;
    }
}