package subsystem2;

import command.Subsystem2Commands;
import util.DatabaseUtil;
import entities.AudioSnimak;
import entities.Kategorija;
import entities.AudioKategorija;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Subsystem2 extends AbstractSubsystem {

    @Resource(lookup = "subsystem2Queue")
    private Queue subsystem2Queue;

    private DatabaseUtil databaseUtil;

    public Subsystem2() {
        databaseUtil = new DatabaseUtil();
    }

    public void init() {
        try {
            Context ctx = new InitialContext();

            subsystem2Queue = (Queue) ctx.lookup("subsystem2Queue");
            this.inputQueue = subsystem2Queue;

            System.out.println("[INFO] Subsystem2 initialized - JMS resources loaded");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to initialize JMS resources: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize JMS resources", e);
        }
    }

    @Override
    public Object handleCommand(String command) {
        try {
            System.out.println("[INFO] Subsystem2 handling command: " + command);

            String[] parts = command.split(":");
            String operation = parts[0];

            switch (operation) {
                // READ operations - return entities
                case Subsystem2Commands.GET_ALL_KATEGORIJE:
                    List<Kategorija> kategorije = databaseUtil.getAllKategorije();
                    System.out.println("[INFO] Subsystem2 returning " + kategorije.size() + " kategorije");
                    return kategorije;

                case Subsystem2Commands.GET_ALL_AUDIO_SNIMCI:
                    List<AudioSnimak> audioSnimci = databaseUtil.getAllAudioSnimci();
                    System.out.println("[INFO] Subsystem2 returning " + audioSnimci.size() + " audio snimci");
                    return audioSnimci;

                case Subsystem2Commands.GET_KATEGORIJE_FOR_AUDIO:
                    if (parts.length < 2) {
                        return null;
                    }
                    int audioId = Integer.parseInt(parts[1]);
                    List<Kategorija> kategorijeForAudio = databaseUtil.getKategorijeForAudio(audioId);
                    return kategorijeForAudio;

                // CREATE operations - return created objects
                case Subsystem2Commands.CREATE_KATEGORIJA:
                    if (parts.length < 2) {
                        return null;
                    }
                    String kategorijaName = parts[1];
                    Object createdKategorija = databaseUtil.createKategorija(kategorijaName);
                    if (createdKategorija instanceof Kategorija) {
                        System.out.println("[INFO] Subsystem2 created kategorija: " + kategorijaName + " with ID: " + ((Kategorija) createdKategorija).getKategorijaId());
                    }
                    return createdKategorija;

                case Subsystem2Commands.CREATE_AUDIO_SNIMAK:
                    if (parts.length < 4) {
                        return null;
                    }
                    String naziv = parts[1];
                    int trajanje = Integer.parseInt(parts[2]);
                    int vlasnikId = Integer.parseInt(parts[3]);

                    Object createdAudioSnimak = databaseUtil.createAudioSnimak(naziv, trajanje, vlasnikId);
                    if (createdAudioSnimak instanceof AudioSnimak) {
                        System.out.println("[INFO] Subsystem2 created audio snimak: " + naziv + " with ID: " + ((AudioSnimak) createdAudioSnimak).getAudioId());
                    }
                    return createdAudioSnimak;

                case Subsystem2Commands.ADD_KATEGORIJA_TO_AUDIO:
                    if (parts.length < 3) {
                        return null;
                    }
                    int audioIdForKategorija = Integer.parseInt(parts[1]);
                    int kategorijaId = Integer.parseInt(parts[2]);

                    Object audioKategorija = databaseUtil.addKategorijaToAudio(audioIdForKategorija, kategorijaId);
                    if (audioKategorija instanceof AudioKategorija) {
                        System.out.println("[INFO] Subsystem2 added kategorija " + kategorijaId + " to audio " + audioIdForKategorija);
                    }
                    return audioKategorija;

                // UPDATE operations - return updated objects
                case Subsystem2Commands.UPDATE_AUDIO_SNIMAK_NAZIV:
                    if (parts.length < 4) {
                        return null;
                    }
                    int audioIdForUpdate = Integer.parseInt(parts[1]);
                    String newNaziv = parts[2];
                    int vlasnikIdForUpdate = Integer.parseInt(parts[3]);

                    Object updatedAudioSnimak = databaseUtil.updateAudioSnimakNaziv(audioIdForUpdate, newNaziv, vlasnikIdForUpdate);
                    if (updatedAudioSnimak instanceof AudioSnimak) {
                        System.out.println("[INFO] Subsystem2 updated naziv for audio ID: " + audioIdForUpdate);
                    }
                    return updatedAudioSnimak;

                // DELETE operations - return boolean
                case Subsystem2Commands.DELETE_AUDIO_SNIMAK:
                    if (parts.length < 3) {
                        return null;
                    }
                    int audioIdForDelete = Integer.parseInt(parts[1]);
                    int vlasnikIdForDelete = Integer.parseInt(parts[2]);

                    Object deleteResult = databaseUtil.deleteAudioSnimak(audioIdForDelete, vlasnikIdForDelete);
                    if (deleteResult instanceof Boolean) {
                        System.out.println("[INFO] Subsystem2 deleted audio snimak ID: " + audioIdForDelete);
                    }
                    return deleteResult;

                default:
                    System.err.println("[ERROR] Subsystem2 - Unknown command: " + operation);
                    return null;
            }

        } catch (Exception e) {
            System.err.println("[ERROR] Subsystem2 command handling error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getSubsystemName() {
        return "Subsystem2";
    }

    @Override
    protected void cleanup() {
        if (databaseUtil != null) {
            databaseUtil.close();
        }
    }
}