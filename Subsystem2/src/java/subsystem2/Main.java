package subsystem2;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("[INFO] Starting Subsystem2 application");
        
        Subsystem2 subsystem = new Subsystem2();
        subsystem.init();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[INFO] Shutting down " + subsystem.getSubsystemName());
            subsystem.stop();
        }));
        
        System.out.println("[INFO] " + subsystem.getSubsystemName() + " is starting...");
        subsystem.start(); // This will block and listen for messages
    }
}