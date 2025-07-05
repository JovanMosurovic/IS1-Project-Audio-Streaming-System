package util;

import entities.Korisnik;
import entities.Mesto;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DatabaseUtil {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(DatabaseUtil.class.getName());
    
    public DatabaseUtil() {
        try {
            emf = Persistence.createEntityManagerFactory("subsystem1PU");
            em = emf.createEntityManager();
            System.out.println("[INFO] Database connection established");
        } catch (Exception e) {
            System.err.println("[ERROR] Database connection failed: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }
    
    public List<Mesto> getAllMesta() {
        try {
            TypedQuery<Mesto> query = em.createNamedQuery("Mesto.findAll", Mesto.class);
            List<Mesto> mesta = query.getResultList();
            System.out.println("[INFO] Retrieved " + mesta.size() + " mesta from database");
            return mesta;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting all mesta: " + e.getMessage());
            throw new RuntimeException("Error getting all mesta", e);
        }
    }
    
    public List<Korisnik> getAllKorisnici() {
        try {
            TypedQuery<Korisnik> query = em.createNamedQuery("Korisnik.findAll", Korisnik.class);
            List<Korisnik> korisnici = query.getResultList();
            System.out.println("[INFO] Retrieved " + korisnici.size() + " korisnici from database");
            return korisnici;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting all korisnici: " + e.getMessage());
            throw new RuntimeException("Error getting all korisnici", e);
        }
    }
    
    public Mesto getMestoById(int mestoId) {
        try {
            Mesto mesto = em.find(Mesto.class, mestoId);
            if (mesto != null) {
                System.out.println("[INFO] Retrieved mesto with ID: " + mestoId);
            } else {
                System.out.println("[WARN] Mesto with ID " + mestoId + " not found");
            }
            return mesto;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting mesto by ID " + mestoId + ": " + e.getMessage());
            throw new RuntimeException("Error getting mesto by ID", e);
        }
    }
    
    public Korisnik getKorisnikById(int korisnikId) {
        try {
            Korisnik korisnik = em.find(Korisnik.class, korisnikId);
            if (korisnik != null) {
                System.out.println("[INFO] Retrieved korisnik with ID: " + korisnikId);
            } else {
                System.out.println("[WARN] Korisnik with ID " + korisnikId + " not found");
            }
            return korisnik;
        } catch (Exception e) {
            System.err.println("[ERROR] Error getting korisnik by ID " + korisnikId + ": " + e.getMessage());
            throw new RuntimeException("Error getting korisnik by ID", e);
        }
    }
    
    public void close() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
        System.out.println("[INFO] Database connection closed");
    }
}