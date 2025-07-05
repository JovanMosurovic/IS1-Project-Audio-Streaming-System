package subsystem1;

import command.Subsystem1Commands;
import util.DatabaseUtil;
import entities.Korisnik;
import entities.Mesto;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.Queue;

public class Subsystem1 extends AbstractSubsystem {
    
    @Resource(lookup = "subsystem1Queue")
    private Queue subsystem1Queue;
    
    private DatabaseUtil databaseUtil;
    
    public Subsystem1() {
        databaseUtil = new DatabaseUtil();
    }
    
    public void init() {
        this.inputQueue = subsystem1Queue;
        System.out.println("[INFO] Subsystem1 initialized");
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
                    if (parts.length < 2) return null;
                    int mestoId = Integer.parseInt(parts[1]);
                    Mesto mesto = databaseUtil.getMestoById(mestoId);
                    return mesto;
                    
                case Subsystem1Commands.GET_KORISNIK_BY_ID:
                    if (parts.length < 2) return null;
                    int korisnikId = Integer.parseInt(parts[1]);
                    Korisnik korisnik = databaseUtil.getKorisnikById(korisnikId);
                    return korisnik;
                    
                // CREATE/UPDATE operations - return success/failure message
                case Subsystem1Commands.CREATE_MESTO:
                    // TODO: Implement mesto creation
                    return "SUCCESS: Mesto created";
                    
                case Subsystem1Commands.CREATE_KORISNIK:
                    // TODO: Implement korisnik creation
                    return "SUCCESS: Korisnik created";
                    
                case Subsystem1Commands.UPDATE_KORISNIK_EMAIL:
                    // TODO: Implement email update
                    return "SUCCESS: Email updated";
                    
                case Subsystem1Commands.UPDATE_KORISNIK_MESTO:
                    // TODO: Implement mesto update
                    return "SUCCESS: Mesto updated";
                    
                default:
                    System.err.println("[ERROR] Subsystem1 - Unknown command: " + operation);
                    return "ERROR: Unknown command: " + operation;
            }
            
        } catch (Exception e) {
            System.err.println("[ERROR] Subsystem1 command handling error: " + e.getMessage());
            return "ERROR: " + e.getMessage();
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