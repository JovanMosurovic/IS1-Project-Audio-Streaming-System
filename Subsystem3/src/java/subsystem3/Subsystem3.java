package subsystem3;

import command.Subsystem3Commands;
import util.DatabaseUtil;
import entities.Paket;
import entities.Pretplata;
import entities.IstorijaSlusanja;
import entities.OmiljeniAudio;
import entities.Ocena;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Subsystem3 extends AbstractSubsystem {

    @Resource(lookup = "subsystem3Queue")
    private Queue subsystem3Queue;

    private DatabaseUtil databaseUtil;

    public Subsystem3() {
        databaseUtil = new DatabaseUtil();
    }

    public void init() {
        try {
            Context ctx = new InitialContext();

            subsystem3Queue = (Queue) ctx.lookup("subsystem3Queue");
            this.inputQueue = subsystem3Queue;

            System.out.println("[INFO] Subsystem3 initialized - JMS resources loaded");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to initialize JMS resources: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize JMS resources", e);
        }
    }

    @Override
    public Object handleCommand(String command) {
        try {
            System.out.println("[INFO] Subsystem3 handling command: " + command);

            String[] parts = command.split(":");
            String operation = parts[0];

            switch (operation) {
                // READ operations - return entities
                case Subsystem3Commands.GET_ALL_PAKETI:
                    List<Paket> paketi = databaseUtil.getAllPaketi();
                    System.out.println("[INFO] Subsystem3 returning " + paketi.size() + " paketi");
                    return paketi;

                case Subsystem3Commands.GET_ALL_PRETPLATE_FOR_KORISNIK:
                    if (parts.length < 2) {
                        return null;
                    }
                    int korisnikId = Integer.parseInt(parts[1]);
                    List<Pretplata> pretplate = databaseUtil.getAllPretplateForKorisnik(korisnikId);
                    return pretplate;

                case Subsystem3Commands.GET_ALL_SLUSANJA_FOR_AUDIO:
                    if (parts.length < 2) {
                        return null;
                    }
                    int audioId = Integer.parseInt(parts[1]);
                    List<IstorijaSlusanja> slusanja = databaseUtil.getAllSlusanjaForAudio(audioId);
                    return slusanja;

                case Subsystem3Commands.GET_ALL_OCENE_FOR_AUDIO:
                    if (parts.length < 2) {
                        return null;
                    }
                    int audioIdForOcene = Integer.parseInt(parts[1]);
                    List<Ocena> ocene = databaseUtil.getAllOceneForAudio(audioIdForOcene);
                    return ocene;

                case Subsystem3Commands.GET_OMILJENI_AUDIO_FOR_KORISNIK:
                    if (parts.length < 2) {
                        return null;
                    }
                    int korisnikIdForOmiljeni = Integer.parseInt(parts[1]);
                    List<OmiljeniAudio> omiljeniAudio = databaseUtil.getOmiljeniAudioForKorisnik(korisnikIdForOmiljeni);
                    return omiljeniAudio;

                // CREATE operations - return created objects
                case Subsystem3Commands.CREATE_PAKET:
                    if (parts.length < 2) {
                        return null;
                    }
                    double trenutnaCena = Double.parseDouble(parts[1]);
                    Object createdPaket = databaseUtil.createPaket(trenutnaCena);
                    if (createdPaket instanceof Paket) {
                        System.out.println("[INFO] Subsystem3 created paket with ID: " + ((Paket) createdPaket).getPaketId());
                    }
                    return createdPaket;

                case Subsystem3Commands.CREATE_PRETPLATA:
                    if (parts.length < 4) {
                        return null;
                    }
                    int korisnikIdForPretplata = Integer.parseInt(parts[1]);
                    int paketId = Integer.parseInt(parts[2]);
                    double placenaCena = Double.parseDouble(parts[3]);

                    Object createdPretplata = databaseUtil.createPretplata(korisnikIdForPretplata, paketId, placenaCena);
                    if (createdPretplata instanceof Pretplata) {
                        System.out.println("[INFO] Subsystem3 created pretplata with ID: " + ((Pretplata) createdPretplata).getPretplataId());
                    }
                    return createdPretplata;

                case Subsystem3Commands.CREATE_SLUSANJE:
                    if (parts.length < 5) {
                        return null;
                    }
                    int korisnikIdForSlusanje = Integer.parseInt(parts[1]);
                    int audioIdForSlusanje = Integer.parseInt(parts[2]);
                    int pocetniSekund = Integer.parseInt(parts[3]);
                    int brojOdslusanihSekundi = Integer.parseInt(parts[4]);

                    Object createdSlusanje = databaseUtil.createSlusanje(korisnikIdForSlusanje, audioIdForSlusanje, pocetniSekund, brojOdslusanihSekundi);
                    if (createdSlusanje instanceof IstorijaSlusanja) {
                        System.out.println("[INFO] Subsystem3 created slusanje with ID: " + ((IstorijaSlusanja) createdSlusanje).getIstorijaSlusanjaId());
                    }
                    return createdSlusanje;

                case Subsystem3Commands.ADD_OMILJENI_AUDIO:
                    if (parts.length < 3) {
                        return null;
                    }
                    int korisnikIdForOmiljeniAdd = Integer.parseInt(parts[1]);
                    int audioIdForOmiljeni = Integer.parseInt(parts[2]);

                    Object createdOmiljeni = databaseUtil.addOmiljeniAudio(korisnikIdForOmiljeniAdd, audioIdForOmiljeni);
                    if (createdOmiljeni instanceof OmiljeniAudio) {
                        System.out.println("[INFO] Subsystem3 added omiljeni audio with ID: " + ((OmiljeniAudio) createdOmiljeni).getOmiljeniAudioId());
                    }
                    return createdOmiljeni;

                case Subsystem3Commands.CREATE_OCENA:
                    if (parts.length < 4) {
                        return null;
                    }
                    int korisnikIdForOcena = Integer.parseInt(parts[1]);
                    int audioIdForOcena = Integer.parseInt(parts[2]);
                    int vrednost = Integer.parseInt(parts[3]);

                    Object createdOcena = databaseUtil.createOcena(korisnikIdForOcena, audioIdForOcena, vrednost);
                    if (createdOcena instanceof Ocena) {
                        System.out.println("[INFO] Subsystem3 created ocena with ID: " + ((Ocena) createdOcena).getOcenaId());
                    }
                    return createdOcena;

                // UPDATE operations - return updated objects
                case Subsystem3Commands.UPDATE_PAKET_CENA:
                    if (parts.length < 3) {
                        return null;
                    }
                    int paketIdForUpdate = Integer.parseInt(parts[1]);
                    double novaCena = Double.parseDouble(parts[2]);

                    Object updatedPaket = databaseUtil.updatePaketCena(paketIdForUpdate, novaCena);
                    if (updatedPaket instanceof Paket) {
                        System.out.println("[INFO] Subsystem3 updated paket cena for ID: " + paketIdForUpdate);
                    }
                    return updatedPaket;

                case Subsystem3Commands.UPDATE_OCENA:
                    if (parts.length < 4) {
                        return null;
                    }
                    int ocenaIdForUpdate = Integer.parseInt(parts[1]);
                    int korisnikIdForOcenaUpdate = Integer.parseInt(parts[2]);
                    int novaVrednost = Integer.parseInt(parts[3]);

                    Object updatedOcena = databaseUtil.updateOcena(ocenaIdForUpdate, korisnikIdForOcenaUpdate, novaVrednost);
                    if (updatedOcena instanceof Ocena) {
                        System.out.println("[INFO] Subsystem3 updated ocena ID: " + ocenaIdForUpdate);
                    }
                    return updatedOcena;

                // DELETE operations - return boolean
                case Subsystem3Commands.DELETE_OCENA:
                    if (parts.length < 3) {
                        return null;
                    }
                    int ocenaIdForDelete = Integer.parseInt(parts[1]);
                    int korisnikIdForOcenaDelete = Integer.parseInt(parts[2]);

                    Object deleteResult = databaseUtil.deleteOcena(ocenaIdForDelete, korisnikIdForOcenaDelete);
                    if (deleteResult instanceof Boolean) {
                        System.out.println("[INFO] Subsystem3 deleted ocena ID: " + ocenaIdForDelete);
                    }
                    return deleteResult;

                default:
                    System.err.println("[ERROR] Subsystem3 - Unknown command: " + operation);
                    return null;
            }

        } catch (Exception e) {
            System.err.println("[ERROR] Subsystem3 command handling error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getSubsystemName() {
        return "Subsystem3";
    }

    @Override
    protected void cleanup() {
        if (databaseUtil != null) {
            databaseUtil.close();
        }
    }
}