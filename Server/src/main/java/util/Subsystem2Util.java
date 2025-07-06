package util;

import command.Subsystem2Commands;
import util.validation.Subsystem2ValidationUtil;
import entities.AudioSnimak;
import entities.Kategorija;
import entities.AudioKategorija;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class Subsystem2Util {

    @Inject
    private JMSUtil jmsUtil;

    @Inject
    private Subsystem2ValidationUtil validator;

    // Kategorija operations
    public Response createKategorija(Map<String, Object> request) {
        String naziv = (String) request.get("naziv");

        Response validationError = validator.validateKategorijaData(naziv);
        if (validationError != null) {
            return validationError;
        }

        String command = Subsystem2Commands.CREATE_KATEGORIJA + ":" + naziv.trim();
        Object result = jmsUtil.sendCommandToSubsystem2(command);

        if (result == null) {
            return Response.status(500).entity("Failed to create kategorija").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof Kategorija) {
            return Response.status(201).entity(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response getAllKategorije() {
        Object kategorije = jmsUtil.sendCommandToSubsystem2(Subsystem2Commands.GET_ALL_KATEGORIJE);
        return kategorije == null ? Response.status(404).entity("No kategorije found").build() : Response.ok(kategorije).build();
    }

    // Audio snimak operations
    public Response createAudioSnimak(Map<String, Object> request) {
        String naziv = (String) request.get("naziv");
        Object trajanjeObj = request.get("trajanje");
        Object vlasnikIdObj = request.get("vlasnikId");

        Response validationError = validator.validateAudioSnimakData(naziv, trajanjeObj, vlasnikIdObj);
        if (validationError != null) {
            return validationError;
        }

        int trajanje = ((Number) trajanjeObj).intValue();
        int vlasnikId = ((Number) vlasnikIdObj).intValue();

        String command = Subsystem2Commands.CREATE_AUDIO_SNIMAK + ":"
                + naziv.trim() + ":" + trajanje + ":" + vlasnikId;

        Object result = jmsUtil.sendCommandToSubsystem2(command);

        if (result == null) {
            return Response.status(500).entity("Failed to create audio snimak").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof AudioSnimak) {
            return Response.status(201).entity(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response getAllAudioSnimci() {
        Object audioSnimci = jmsUtil.sendCommandToSubsystem2(Subsystem2Commands.GET_ALL_AUDIO_SNIMCI);
        return audioSnimci == null ? Response.status(404).entity("No audio snimci found").build() : Response.ok(audioSnimci).build();
    }

    public Response updateAudioSnimakNaziv(int audioId, Map<String, Object> request) {
        if (audioId <= 0) {
            return Response.status(400).entity("Audio ID must be a positive number").build();
        }

        String naziv = (String) request.get("naziv");
        Object vlasnikIdObj = request.get("vlasnikId");

        if (naziv == null || naziv.trim().isEmpty()) {
            return Response.status(400).entity("Naziv is required").build();
        }

        if (vlasnikIdObj == null) {
            return Response.status(400).entity("Vlasnik ID is required").build();
        }

        int vlasnikId = ((Number) vlasnikIdObj).intValue();

        String command = Subsystem2Commands.UPDATE_AUDIO_SNIMAK_NAZIV + ":" + audioId + ":" + naziv.trim() + ":" + vlasnikId;
        Object result = jmsUtil.sendCommandToSubsystem2(command);

        if (result == null) {
            return Response.status(500).entity("Failed to update audio snimak naziv").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof AudioSnimak) {
            return Response.ok(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response deleteAudioSnimak(int audioId, Map<String, Object> request) {
        if (audioId <= 0) {
            return Response.status(400).entity("Audio ID must be a positive number").build();
        }

        Object vlasnikIdObj = request.get("vlasnikId");

        if (vlasnikIdObj == null) {
            return Response.status(400).entity("Vlasnik ID is required").build();
        }

        int vlasnikId = ((Number) vlasnikIdObj).intValue();

        String command = Subsystem2Commands.DELETE_AUDIO_SNIMAK + ":" + audioId + ":" + vlasnikId;
        Object result = jmsUtil.sendCommandToSubsystem2(command);

        if (result == null) {
            return Response.status(500).entity("Failed to delete audio snimak").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof Boolean && (Boolean) result) {
            return Response.ok().entity("Audio snimak deleted successfully").build();
        }

        return Response.status(500).entity("Failed to delete audio snimak").build();
    }

    // Audio-Kategorija operations
    public Response addKategorijaToAudio(int audioId, Map<String, Object> request) {
        if (audioId <= 0) {
            return Response.status(400).entity("Audio ID must be a positive number").build();
        }

        Object kategorijaIdObj = request.get("kategorijaId");

        Response validationError = validator.validateAudioKategorijaData(audioId, kategorijaIdObj);
        if (validationError != null) {
            return validationError;
        }

        int kategorijaId = ((Number) kategorijaIdObj).intValue();

        String command = Subsystem2Commands.ADD_KATEGORIJA_TO_AUDIO + ":" + audioId + ":" + kategorijaId;
        Object result = jmsUtil.sendCommandToSubsystem2(command);

        if (result == null) {
            return Response.status(500).entity("Failed to add kategorija to audio").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof AudioKategorija) {
            return Response.status(201).entity(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response getKategorijeForAudio(int audioId) {
        if (audioId <= 0) {
            return Response.status(400).entity("Audio ID must be a positive number").build();
        }

        String command = Subsystem2Commands.GET_KATEGORIJE_FOR_AUDIO + ":" + audioId;
        Object kategorije = jmsUtil.sendCommandToSubsystem2(command);

        return kategorije == null ? Response.status(404).entity("No kategorije found for audio " + audioId).build() : Response.ok(kategorije).build();
    }
}