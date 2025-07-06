package server.resources;

import java.util.HashMap;
import util.Subsystem1Util;
import util.Subsystem2Util;
import util.Subsystem3Util;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    @POST
    @Path("kategorija")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createKategorija(Map<String, Object> request) {
        return subsystem2Util.createKategorija(request);
    }

    @POST
    @Path("audio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAudioSnimak(Map<String, Object> request) {
        return subsystem2Util.createAudioSnimak(request);
    }

    @PUT
    @Path("audio/{id}/naziv")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAudioSnimakNaziv(@PathParam("id") int audioId, Map<String, Object> request) {
        return subsystem2Util.updateAudioSnimakNaziv(audioId, request);
    }

    @POST
    @Path("audio/{audioId}/kategorija")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKategorijaToAudio(@PathParam("audioId") int audioId, Map<String, Object> request) {
        return subsystem2Util.addKategorijaToAudio(audioId, request);
    }

    @DELETE
    @Path("audio/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAudioSnimak(@PathParam("id") int audioId, @QueryParam("vlasnikId") int vlasnikId) {
        Map<String, Object> request = new HashMap<>();
        request.put("vlasnikId", vlasnikId);

        return subsystem2Util.deleteAudioSnimak(audioId, request);
    }

    @GET
    @Path("kategorija")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKategorije() {
        return subsystem2Util.getAllKategorije();
    }

    @GET
    @Path("audio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAudioSnimci() {
        return subsystem2Util.getAllAudioSnimci();
    }

    @GET
    @Path("audio/{id}/kategorije")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKategorijeForAudio(@PathParam("id") int audioId) {
        return subsystem2Util.getKategorijeForAudio(audioId);
    }

    // ============== SUBSYSTEM 3 ENDPOINTS ==============
    @POST
    @Path("paket")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPaket(Map<String, Object> request) {
        return subsystem3Util.createPaket(request);
    }

    @PUT
    @Path("paket/{id}/cena")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePaketCena(@PathParam("id") int paketId, Map<String, Object> request) {
        return subsystem3Util.updatePaketCena(paketId, request);
    }

    @POST
    @Path("pretplata")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPretplata(Map<String, Object> request) {
        return subsystem3Util.createPretplata(request);
    }

    @POST
    @Path("slusanje")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSlusanje(Map<String, Object> request) {
        return subsystem3Util.createSlusanje(request);
    }

    @POST
    @Path("omiljeni")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOmiljeniAudio(Map<String, Object> request) {
        return subsystem3Util.addOmiljeniAudio(request);
    }

    @POST
    @Path("ocena")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOcena(Map<String, Object> request) {
        return subsystem3Util.createOcena(request);
    }

    @PUT
    @Path("ocena/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOcena(@PathParam("id") int ocenaId, Map<String, Object> request) {
        return subsystem3Util.updateOcena(ocenaId, request);
    }

    @DELETE
    @Path("ocena/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOcena(@PathParam("id") int ocenaId, @QueryParam("korisnikId") int korisnikId) {
        Map<String, Object> request = new HashMap<>();
        request.put("korisnikId", korisnikId);
        return subsystem3Util.deleteOcena(ocenaId, request);
    }

    @GET
    @Path("paket")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPaketi() {
        return subsystem3Util.getAllPaketi();
    }

    @GET
    @Path("pretplata/korisnik/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPretplateForKorisnik(@PathParam("id") int korisnikId) {
        return subsystem3Util.getAllPretplateForKorisnik(korisnikId);
    }

    @GET
    @Path("slusanje/audio/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSlusanjaForAudio(@PathParam("id") int audioId) {
        return subsystem3Util.getAllSlusanjaForAudio(audioId);
    }

    @GET
    @Path("ocena/audio/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOceneForAudio(@PathParam("id") int audioId) {
        return subsystem3Util.getAllOceneForAudio(audioId);
    }

    @GET
    @Path("omiljeni/korisnik/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOmiljeniAudioForKorisnik(@PathParam("id") int korisnikId) {
        return subsystem3Util.getOmiljeniAudioForKorisnik(korisnikId);
    }
}
