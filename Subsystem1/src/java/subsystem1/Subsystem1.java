package subsystem1;

import command.Subsystem1Commands;
import util.DatabaseUtil;
import entities.Korisnik;
import entities.Mesto;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Subsystem1 extends AbstractSubsystem {

    @Resource(lookup = "subsystem1Queue")
    private Queue subsystem1Queue;

    private DatabaseUtil databaseUtil;

    public Subsystem1() {
        databaseUtil = new DatabaseUtil();
    }

    public void init() {
        try {
            Context ctx = new InitialContext();

            subsystem1Queue = (Queue) ctx.lookup("subsystem1Queue");

            this.inputQueue = subsystem1Queue;

            System.out.println("[INFO] Subsystem1 initialized - JMS resources loaded");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to initialize JMS resources: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize JMS resources", e);
        }
    }

    @Override
    public Object handleCommand(String command) {
        try {
            System.out.println("[INFO] Subsystem1 handling command: " + command);

            if (command.equals(Subsystem1Commands.TEST_MESSAGE)) {
                return "SUCCESS: Test message received by " + getSubsystemName();
            }

            String[] parts = command.split(":");
            String operation = parts[0];

            switch (operation) {
                // READ operations - return entities
                case Subsystem1Commands.GET_ALL_MESTA:
                    List<Mesto> mesta = databaseUtil.getAllMesta();
                    System.out.println("[INFO] Subsystem1 returning " + mesta.size() + " mesta");
                    return mesta;

                case Subsystem1Commands.GET_ALL_KORISNICI:
                    List<Korisnik> korisnici = databaseUtil.getAllKorisnici();
                    System.out.println("[INFO] Subsystem1 returning " + korisnici.size() + " korisnici");
                    return korisnici;

                case Subsystem1Commands.GET_MESTO_BY_ID:
                    if (parts.length < 2) {
                        return null;
                    }
                    int mestoId = Integer.parseInt(parts[1]);
                    Mesto mesto = databaseUtil.getMestoById(mestoId);
                    return mesto;

                case Subsystem1Commands.GET_KORISNIK_BY_ID:
                    if (parts.length < 2) {
                        return null;
                    }
                    int korisnikId = Integer.parseInt(parts[1]);
                    Korisnik korisnik = databaseUtil.getKorisnikById(korisnikId);
                    return korisnik;

                // CREATE operations - return created objects
                case Subsystem1Commands.CREATE_MESTO:
                    if (parts.length < 2) {
                        return null;
                    }
                    String naziv = parts[1];
                    Object createdMesto = databaseUtil.createMesto(naziv);
                    if (createdMesto instanceof Mesto) {
                        System.out.println("[INFO] Subsystem1 created mesto: " + naziv + " with ID: " + ((Mesto) createdMesto).getMestoId());
                    }
                    return createdMesto;

                case Subsystem1Commands.CREATE_KORISNIK:
                    if (parts.length < 6) {
                        return null;
                    }
                    String ime = parts[1];
                    String email = parts[2];
                    int godiste = Integer.parseInt(parts[3]);
                    String pol = parts[4];
                    int mestoIdForKorisnik = Integer.parseInt(parts[5]);

                    Object createdKorisnik = databaseUtil.createKorisnik(ime, email, godiste, pol, mestoIdForKorisnik);
                    if (createdKorisnik instanceof Korisnik) {
                        System.out.println("[INFO] Subsystem1 created korisnik: " + ime + " with ID: " + ((Korisnik) createdKorisnik).getKorisnikId());
                    }
                    return createdKorisnik;

                // UPDATE operations - return updated objects
                case Subsystem1Commands.UPDATE_KORISNIK_EMAIL:
                    if (parts.length < 3) {
                        return null;
                    }
                    int korisnikIdForEmail = Integer.parseInt(parts[1]);
                    String newEmail = parts[2];

                    Object updatedKorisnik = databaseUtil.updateKorisnikEmail(korisnikIdForEmail, newEmail);
                    if (updatedKorisnik instanceof Korisnik) {
                        System.out.println("[INFO] Subsystem1 updated email for korisnik ID: " + korisnikIdForEmail);
                    }
                    return updatedKorisnik;

                case Subsystem1Commands.UPDATE_KORISNIK_MESTO:
                    if (parts.length < 3) {
                        return null;
                    }
                    int korisnikIdForMesto = Integer.parseInt(parts[1]);
                    int newMestoId = Integer.parseInt(parts[2]);

                    Object updatedKorisnikMesto = databaseUtil.updateKorisnikMesto(korisnikIdForMesto, newMestoId);
                    if (updatedKorisnikMesto instanceof Korisnik) {
                        System.out.println("[INFO] Subsystem1 updated mesto for korisnik ID: " + korisnikIdForMesto);
                    }
                    return updatedKorisnikMesto;

                default:
                    System.err.println("[ERROR] Subsystem1 - Unknown command: " + operation);
                    return null;
            }

        } catch (Exception e) {
            System.err.println("[ERROR] Subsystem1 command handling error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getSubsystemName() {
        return "Subsystem1";
    }

    @Override
    protected void cleanup() {
        if (databaseUtil != null) {
            databaseUtil.close();
        }
    }
}
