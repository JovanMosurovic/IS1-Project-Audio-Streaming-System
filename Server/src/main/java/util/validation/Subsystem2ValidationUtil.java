package util.validation;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class Subsystem2ValidationUtil {
    
    // Audio snimak validation
    public Response validateAudioSnimakData(String naziv, Object trajanjeObj, Object vlasnikIdObj) {
        // Validate naziv
        if (naziv == null || naziv.trim().isEmpty()) {
            return Response.status(400).entity("Audio snimak naziv is required").build();
        }
        if (naziv.length() > 200) {
            return Response.status(400).entity("Audio snimak naziv must be less than 200 characters").build();
        }
        if (naziv.trim().length() == 0) {
            return Response.status(400).entity("Audio snimak naziv cannot be only whitespace").build();
        }
        
        // Validate trajanje
        if (trajanjeObj == null) {
            return Response.status(400).entity("Audio snimak trajanje is required").build();
        }
        
        int trajanje;
        try {
            trajanje = ((Number) trajanjeObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Audio snimak trajanje must be a valid number").build();
        }
        
        if (trajanje <= 0) {
            return Response.status(400).entity("Audio snimak trajanje must be greater than 0 seconds").build();
        }
        
        // Validate vlasnik_id
        if (vlasnikIdObj == null) {
            return Response.status(400).entity("Audio snimak vlasnikId is required").build();
        }
        
        int vlasnikId;
        try {
            vlasnikId = ((Number) vlasnikIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Audio snimak vlasnikId must be a valid number").build();
        }
        
        if (vlasnikId <= 0) {
            return Response.status(400).entity("Audio snimak vlasnikId must be a positive number").build();
        }
        
        return null;
    }
    
    // Kategorija validation
    public Response validateKategorijaData(String naziv) {
        if (naziv == null || naziv.trim().isEmpty()) {
            return Response.status(400).entity("Kategorija naziv is required").build();
        }
        
        if (naziv.length() > 100) {
            return Response.status(400).entity("Kategorija naziv must be less than 100 characters").build();
        }
        
        if (naziv.trim().length() == 0) {
            return Response.status(400).entity("Kategorija naziv cannot be only whitespace").build();
        }
        
        return null;
    }
    
    // Audio-Kategorija validation
    public Response validateAudioKategorijaData(Object audioIdObj, Object kategorijaIdObj) {
        // Validate audioId
        if (audioIdObj == null) {
            return Response.status(400).entity("Audio ID is required").build();
        }
        
        int audioId;
        try {
            audioId = ((Number) audioIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Audio ID must be a valid number").build();
        }
        
        if (audioId <= 0) {
            return Response.status(400).entity("Audio ID must be a positive number").build();
        }
        
        // Validate kategorijaId
        if (kategorijaIdObj == null) {
            return Response.status(400).entity("Kategorija ID is required").build();
        }
        
        int kategorijaId;
        try {
            kategorijaId = ((Number) kategorijaIdObj).intValue();
        } catch (Exception e) {
            return Response.status(400).entity("Kategorija ID must be a valid number").build();
        }
        
        if (kategorijaId <= 0) {
            return Response.status(400).entity("Kategorija ID must be a positive number").build();
        }
        
        return null;
    }
}