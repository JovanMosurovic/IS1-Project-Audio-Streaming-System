package subsystem1;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("[INFO] Starting Subsystem1 application");
        
        Subsystem1 subsystem = new Subsystem1();
        subsystem.init();
        
        // Add shutdown hook for graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[INFO] Shutting down " + subsystem.getSubsystemName());
            subsystem.stop();
        }));
        
        System.out.println("[INFO] " + subsystem.getSubsystemName() + " is starting...");
        subsystem.start(); // This will block and listen for messages
    }
}