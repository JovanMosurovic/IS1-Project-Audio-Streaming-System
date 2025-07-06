package util;

import command.Subsystem3Commands;
import util.validation.Subsystem3ValidationUtil;
import entities.Paket;
import entities.Pretplata;
import entities.IstorijaSlusanja;
import entities.OmiljeniAudio;
import entities.Ocena;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class Subsystem3Util {

    @Inject
    private JMSUtil jmsUtil;

    @Inject
    private Subsystem3ValidationUtil validator;

    // Paket operations
    public Response createPaket(Map<String, Object> request) {
        Object trenutnaCenaObj = request.get("trenutnaCena");

        Response validationError = validator.validatePaketData(trenutnaCenaObj);
        if (validationError != null) {
            return validationError;
        }

        double trenutnaCena = ((Number) trenutnaCenaObj).doubleValue();

        String command = Subsystem3Commands.CREATE_PAKET + ":" + trenutnaCena;
        Object result = jmsUtil.sendCommandToSubsystem3(command);

        if (result == null) {
            return Response.status(500).entity("Failed to create paket").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof Paket) {
            return Response.status(201).entity(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response updatePaketCena(int paketId, Map<String, Object> request) {
        if (paketId <= 0) {
            return Response.status(400).entity("Paket ID must be a positive number").build();
        }

        Object trenutnaCenaObj = request.get("trenutnaCena");

        Response validationError = validator.validatePaketData(trenutnaCenaObj);
        if (validationError != null) {
            return validationError;
        }

        double trenutnaCena = ((Number) trenutnaCenaObj).doubleValue();

        String command = Subsystem3Commands.UPDATE_PAKET_CENA + ":" + paketId + ":" + trenutnaCena;
        Object result = jmsUtil.sendCommandToSubsystem3(command);

        if (result == null) {
            return Response.status(500).entity("Failed to update paket cena").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof Paket) {
            return Response.ok(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response getAllPaketi() {
        Object paketi = jmsUtil.sendCommandToSubsystem3(Subsystem3Commands.GET_ALL_PAKETI);
        return paketi == null ? Response.status(404).entity("No paketi found").build() : Response.ok(paketi).build();
    }

    // Pretplata operations
    public Response createPretplata(Map<String, Object> request) {
        Object korisnikIdObj = request.get("korisnikId");
        Object paketIdObj = request.get("paketId");
        Object placenaCenaObj = request.get("placenaCena");

        Response validationError = validator.validatePretplataData(korisnikIdObj, paketIdObj, placenaCenaObj);
        if (validationError != null) {
            return validationError;
        }

        int korisnikId = ((Number) korisnikIdObj).intValue();
        int paketId = ((Number) paketIdObj).intValue();
        double placenaCena = ((Number) placenaCenaObj).doubleValue();

        String command = Subsystem3Commands.CREATE_PRETPLATA + ":" + korisnikId + ":" + paketId + ":" + placenaCena;
        Object result = jmsUtil.sendCommandToSubsystem3(command);

        if (result == null) {
            return Response.status(500).entity("Failed to create pretplata").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof Pretplata) {
            return Response.status(201).entity(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response getAllPretplateForKorisnik(int korisnikId) {
        if (korisnikId <= 0) {
            return Response.status(400).entity("Korisnik ID must be a positive number").build();
        }

        String command = Subsystem3Commands.GET_ALL_PRETPLATE_FOR_KORISNIK + ":" + korisnikId;
        Object pretplate = jmsUtil.sendCommandToSubsystem3(command);

        return pretplate == null ? Response.status(404).entity("No pretplate found for korisnik " + korisnikId).build() : Response.ok(pretplate).build();
    }

    // Istorija slusanja operations
    public Response createSlusanje(Map<String, Object> request) {
        Object korisnikIdObj = request.get("korisnikId");
        Object audioIdObj = request.get("audioId");
        Object pocetniSekObj = request.get("pocetniSekund");
        Object brojSekObj = request.get("brojOdslusanihSekundi");

        Response validationError = validator.validateIstorijaData(korisnikIdObj, audioIdObj, pocetniSekObj, brojSekObj);
        if (validationError != null) {
            return validationError;
        }

        int korisnikId = ((Number) korisnikIdObj).intValue();
        int audioId = ((Number) audioIdObj).intValue();
        int pocetniSekund = ((Number) pocetniSekObj).intValue();
        int brojOdslusanihSekundi = ((Number) brojSekObj).intValue();

        String command = Subsystem3Commands.CREATE_SLUSANJE + ":" + korisnikId + ":" + audioId + ":" + pocetniSekund + ":" + brojOdslusanihSekundi;
        Object result = jmsUtil.sendCommandToSubsystem3(command);

        if (result == null) {
            return Response.status(500).entity("Failed to create slusanje").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof IstorijaSlusanja) {
            return Response.status(201).entity(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response getAllSlusanjaForAudio(int audioId) {
        if (audioId <= 0) {
            return Response.status(400).entity("Audio ID must be a positive number").build();
        }

        String command = Subsystem3Commands.GET_ALL_SLUSANJA_FOR_AUDIO + ":" + audioId;
        Object slusanja = jmsUtil.sendCommandToSubsystem3(command);

        return slusanja == null ? Response.status(404).entity("No slusanja found for audio " + audioId).build() : Response.ok(slusanja).build();
    }

    // Omiljeni audio operations
    public Response addOmiljeniAudio(Map<String, Object> request) {
        Object korisnikIdObj = request.get("korisnikId");
        Object audioIdObj = request.get("audioId");

        Response validationError = validator.validateOmiljeniAudioData(korisnikIdObj, audioIdObj);
        if (validationError != null) {
            return validationError;
        }

        int korisnikId = ((Number) korisnikIdObj).intValue();
        int audioId = ((Number) audioIdObj).intValue();

        String command = Subsystem3Commands.ADD_OMILJENI_AUDIO + ":" + korisnikId + ":" + audioId;
        Object result = jmsUtil.sendCommandToSubsystem3(command);

        if (result == null) {
            return Response.status(500).entity("Failed to add omiljeni audio").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof OmiljeniAudio) {
            return Response.status(201).entity(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response getOmiljeniAudioForKorisnik(int korisnikId) {
        if (korisnikId <= 0) {
            return Response.status(400).entity("Korisnik ID must be a positive number").build();
        }

        String command = Subsystem3Commands.GET_OMILJENI_AUDIO_FOR_KORISNIK + ":" + korisnikId;
        Object omiljeniAudio = jmsUtil.sendCommandToSubsystem3(command);

        return omiljeniAudio == null ? Response.status(404).entity("No omiljeni audio found for korisnik " + korisnikId).build() : Response.ok(omiljeniAudio).build();
    }

    // Ocena operations
    public Response createOcena(Map<String, Object> request) {
        Object korisnikIdObj = request.get("korisnikId");
        Object audioIdObj = request.get("audioId");
        Object vrednostObj = request.get("vrednost");

        Response validationError = validator.validateOcenaData(korisnikIdObj, audioIdObj, vrednostObj);
        if (validationError != null) {
            return validationError;
        }

        int korisnikId = ((Number) korisnikIdObj).intValue();
        int audioId = ((Number) audioIdObj).intValue();
        int vrednost = ((Number) vrednostObj).intValue();

        String command = Subsystem3Commands.CREATE_OCENA + ":" + korisnikId + ":" + audioId + ":" + vrednost;
        Object result = jmsUtil.sendCommandToSubsystem3(command);

        if (result == null) {
            return Response.status(500).entity("Failed to create ocena").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof Ocena) {
            return Response.status(201).entity(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response updateOcena(int ocenaId, Map<String, Object> request) {
        if (ocenaId <= 0) {
            return Response.status(400).entity("Ocena ID must be a positive number").build();
        }

        Object korisnikIdObj = request.get("korisnikId");
        Object vrednostObj = request.get("vrednost");

        if (korisnikIdObj == null) {
            return Response.status(400).entity("Korisnik ID is required").build();
        }

        if (vrednostObj == null) {
            return Response.status(400).entity("Vrednost is required").build();
        }

        int korisnikId = ((Number) korisnikIdObj).intValue();
        int vrednost = ((Number) vrednostObj).intValue();

        if (vrednost < 1 || vrednost > 5) {
            return Response.status(400).entity("Vrednost must be between 1 and 5").build();
        }

        String command = Subsystem3Commands.UPDATE_OCENA + ":" + ocenaId + ":" + korisnikId + ":" + vrednost;
        Object result = jmsUtil.sendCommandToSubsystem3(command);

        if (result == null) {
            return Response.status(500).entity("Failed to update ocena").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof Ocena) {
            return Response.ok(result).build();
        }

        return Response.status(500).entity("Unexpected response type: " + result.getClass().getName()).build();
    }

    public Response deleteOcena(int ocenaId, Map<String, Object> request) {
        if (ocenaId <= 0) {
            return Response.status(400).entity("Ocena ID must be a positive number").build();
        }

        Object korisnikIdObj = request.get("korisnikId");

        if (korisnikIdObj == null) {
            return Response.status(400).entity("Korisnik ID is required").build();
        }

        int korisnikId = ((Number) korisnikIdObj).intValue();

        String command = Subsystem3Commands.DELETE_OCENA + ":" + ocenaId + ":" + korisnikId;
        Object result = jmsUtil.sendCommandToSubsystem3(command);

        if (result == null) {
            return Response.status(500).entity("Failed to delete ocena").build();
        }

        if (result instanceof DatabaseError) {
            DatabaseError error = (DatabaseError) result;
            return Response.status(error.getErrorCode()).entity(error.getErrorMessage()).build();
        }

        if (result instanceof Boolean && (Boolean) result) {
            return Response.ok().entity("Ocena deleted successfully").build();
        }

        return Response.status(500).entity("Failed to delete ocena").build();
    }

    public Response getAllOceneForAudio(int audioId) {
        if (audioId <= 0) {
            return Response.status(400).entity("Audio ID must be a positive number").build();
        }

        String command = Subsystem3Commands.GET_ALL_OCENE_FOR_AUDIO + ":" + audioId;
        Object ocene = jmsUtil.sendCommandToSubsystem3(command);

        return ocene == null ? Response.status(404).entity("No ocene found for audio " + audioId).build() : Response.ok(ocene).build();
    }
}